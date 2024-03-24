package com.example.bankingapp.data.repository.remote

import com.example.bankingapp.data.common.Resource
import com.example.bankingapp.data.remote.mapper.toDomain
import com.example.bankingapp.data.remote.model.CardDto
import com.example.bankingapp.data.remote.model.TransactionDto
import com.example.bankingapp.domain.model.CardDomain
import com.example.bankingapp.domain.model.TransactionDomain
import com.example.bankingapp.domain.repository.ITransactionsRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TransactionsRepositoryImpl @Inject constructor(
    firebaseDb: DatabaseReference,
    firebaseAuth: FirebaseAuth
) : ITransactionsRepository {
    private val uid = firebaseAuth.currentUser?.uid
    private val database = firebaseDb.child(uid!!).child("Transactions")
    override suspend fun getTransactions(): Flow<Resource<List<TransactionDomain>>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                val dataList: MutableList<TransactionDomain> = mutableListOf()
                val dataSnapshot: DataSnapshot = withContext(Dispatchers.IO) {
                    database.get().await()
                }
//                for (i in dataSnapshot.children) {
//                    val transactionDtoData = TransactionDto(
//                        i.getValue(CardDto::class.java)!!.amountEUR,
//                        i.getValue(CardDto::class.java)!!.amountGEL,
//                        i.getValue(CardDto::class.java)!!.amountUSD,
//                        i.getValue(CardDto::class.java)!!.cardNum,
//                        i.getValue(CardDto::class.java)!!.cvv,
//                        i.getValue(CardDto::class.java)!!.id,
//                        i.getValue(CardDto::class.java)!!.validDate,
//                    )
//                    dataList.add(transactionDtoData.toDomain())
//                }
                emit(Resource.Success(dataList))
            } catch (e: Throwable) {
                emit(Resource.Error(e.message ?: ""))
            }
            emit(Resource.Loading(false))
        }
    }

    override suspend fun saveTransaction(transaction: TransactionDomain): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                database.child(transaction.id).setValue(transaction)
                emit(Resource.Success(true))

            } catch (e: Throwable) {
                emit(Resource.Error(e.message ?: ""))
            }
            emit(Resource.Loading(false))
        }
    }
}
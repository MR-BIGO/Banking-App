package com.example.bankingapp.data.repository.remote

import android.util.Log.d
import com.example.bankingapp.data.common.Resource
import com.example.bankingapp.data.remote.mapper.toDomain
import com.example.bankingapp.data.remote.model.CardDto
import com.example.bankingapp.domain.model.CardDomain
import com.example.bankingapp.domain.repository.ICardsRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CardsRepositoryImpl @Inject constructor(
    firebaseDb: DatabaseReference,
    firebaseAuth: FirebaseAuth
) : ICardsRepository {
    private val uid = firebaseAuth.currentUser?.uid
    private val database = firebaseDb.child(uid!!).child("Cards")
    override suspend fun getCards(): Flow<Resource<List<CardDomain>>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                val dataList: MutableList<CardDomain> = mutableListOf()
                val dataSnapshot: DataSnapshot = withContext(Dispatchers.IO) {
                    database.get().await()
                }
                for (i in dataSnapshot.children) {
                    val cardDtoData = CardDto(
                        i.getValue(CardDto::class.java)!!.amountEUR,
                        i.getValue(CardDto::class.java)!!.amountGEL,
                        i.getValue(CardDto::class.java)!!.amountUSD,
                        i.getValue(CardDto::class.java)!!.cardNum,
                        i.getValue(CardDto::class.java)!!.cvv,
                        i.getValue(CardDto::class.java)!!.id,
                        i.getValue(CardDto::class.java)!!.validDate,
                    )
                    dataList.add(cardDtoData.toDomain())
                }
                emit(Resource.Success(dataList))
            } catch (e: Throwable) {
                emit(Resource.Error(e.message ?: ""))
            }
            emit(Resource.Loading(false))
        }
    }

    override suspend fun getCardById(id: String): Flow<Resource<CardDomain>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                val dataSnapshot: DataSnapshot = withContext(Dispatchers.IO) {
                    database.child(id).get().await()
                }
                val cardDtoData = CardDto(
                    dataSnapshot.getValue(CardDto::class.java)!!.amountEUR,
                    dataSnapshot.getValue(CardDto::class.java)!!.amountGEL,
                    dataSnapshot.getValue(CardDto::class.java)!!.amountUSD,
                    dataSnapshot.getValue(CardDto::class.java)!!.cardNum,
                    dataSnapshot.getValue(CardDto::class.java)!!.cvv,
                    dataSnapshot.getValue(CardDto::class.java)!!.id,
                    dataSnapshot.getValue(CardDto::class.java)!!.validDate,
                )
                emit(Resource.Success(cardDtoData.toDomain()))
            } catch (e: Throwable) {
                emit(Resource.Error(e.message ?: ""))
            }
            emit(Resource.Loading(false))
        }
    }

    override suspend fun saveCard(card: CardDomain): Flow<Resource<String>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                database.child(card.id).setValue(card)
                emit(Resource.Success("Card Added Successfully"))

            } catch (e: Throwable) {
                emit(Resource.Error(e.message ?: ""))
            }
            emit(Resource.Loading(false))
        }
    }

    override suspend fun updateCard(card: CardDomain): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                database.child(card.id).setValue(card)
                emit(Resource.Success(true))
            } catch (e: Throwable) {
                emit(Resource.Error(e.message ?: ""))
            }
            emit(Resource.Loading(false))
        }
    }

    override fun deleteCard(id: String): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                database.child(id).removeValue()
                emit(Resource.Success(true))
            } catch (e: Throwable) {
                emit(Resource.Error(e.message ?: ""))
            }
            emit(Resource.Loading(false))
        }
    }
}
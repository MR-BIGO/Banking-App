package com.example.bankingapp.data.repository.remote

import com.example.bankingapp.data.common.Resource
import com.example.bankingapp.domain.model.CardDomain
import com.example.bankingapp.domain.repository.ICardsRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class CardsRepositoryImpl @Inject constructor(
    private val firebaseDb: DatabaseReference,
    private val firebaseAuth: FirebaseAuth
) : ICardsRepository {
    val uid = firebaseAuth.currentUser?.uid
    val database = firebaseDb.child(uid!!).child("Cards")
    override suspend fun getCards(): Flow<Resource<List<CardDomain>>> {
        return flow {

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

    override suspend fun updateCard(card: CardDomain): Flow<Resource<String>> {
        return flow {

        }
    }

    override fun deleteCard(id: Int): Flow<Resource<String>> {
        return flow {

        }
    }
}
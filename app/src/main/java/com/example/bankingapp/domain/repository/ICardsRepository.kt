package com.example.bankingapp.domain.repository

import com.example.bankingapp.data.common.Resource
import com.example.bankingapp.domain.model.CardDomain
import kotlinx.coroutines.flow.Flow

interface ICardsRepository {

    suspend fun getCards(): Flow<Resource<List<CardDomain>>>
    suspend fun getCardById(id: String): Flow<Resource<CardDomain>>
    suspend fun saveCard(card: CardDomain): Flow<Resource<String>>
    suspend fun updateCard(card: CardDomain): Flow<Resource<Boolean>>
    fun deleteCard(id: String): Flow<Resource<Boolean>>
}
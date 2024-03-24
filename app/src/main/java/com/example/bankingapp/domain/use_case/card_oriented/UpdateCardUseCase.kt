package com.example.bankingapp.domain.use_case.card_oriented

import com.example.bankingapp.data.common.Resource
import com.example.bankingapp.domain.model.CardDomain
import com.example.bankingapp.domain.repository.ICardsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateCardUseCase @Inject constructor(private val repository: ICardsRepository) {
    suspend operator fun invoke(card: CardDomain): Flow<Resource<Boolean>>{
        return repository.updateCard(card)
    }
}
package com.example.bankingapp.domain.use_case.card_oriented

import com.example.bankingapp.domain.model.CardDomain
import com.example.bankingapp.domain.repository.ICardsRepository
import javax.inject.Inject

class SaveCardUseCase @Inject constructor(private val repository: ICardsRepository) {

    suspend operator fun invoke(card: CardDomain){
        repository.saveCard(card)
    }
}
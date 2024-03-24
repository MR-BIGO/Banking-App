package com.example.bankingapp.domain.use_case.card_oriented

import com.example.bankingapp.data.common.Resource
import com.example.bankingapp.domain.repository.ICardsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteCardUseCase @Inject constructor(private val repository: ICardsRepository) {
    operator fun invoke(id: String): Flow<Resource<Boolean>> {
        return repository.deleteCard(id)
    }
}
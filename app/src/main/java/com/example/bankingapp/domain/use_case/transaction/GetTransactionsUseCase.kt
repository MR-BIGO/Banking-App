package com.example.bankingapp.domain.use_case.transaction

import com.example.bankingapp.data.common.Resource
import com.example.bankingapp.domain.model.TransactionDomain
import com.example.bankingapp.domain.repository.ITransactionsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTransactionsUseCase @Inject constructor(private val repository: ITransactionsRepository) {
    suspend operator fun invoke(): Flow<Resource<List<TransactionDomain>>>{
        return repository.getTransactions()
    }
}
package com.example.bankingapp.domain.repository

import com.example.bankingapp.data.common.Resource
import com.example.bankingapp.domain.model.TransactionDomain
import kotlinx.coroutines.flow.Flow

interface ITransactionsRepository {

    suspend fun getTransactions(): Flow<Resource<List<TransactionDomain>>>
    suspend fun saveTransaction(transaction: TransactionDomain)
}
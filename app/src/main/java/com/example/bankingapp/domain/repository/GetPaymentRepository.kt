package com.example.bankingapp.domain.repository

import com.example.bankingapp.data.common.Resource
import com.example.bankingapp.domain.model.PaymentDomain
import kotlinx.coroutines.flow.Flow

interface GetPaymentRepository {
    suspend fun  getPayments(): Flow<Resource<List<PaymentDomain>>>
}
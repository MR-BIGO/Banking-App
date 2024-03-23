package com.example.bankingapp.data.repository.remote

import com.example.bankingapp.data.common.Resource
import com.example.bankingapp.data.remote.mapper.base.mapListToDomain
import com.example.bankingapp.data.remote.mapper.toDomain
import com.example.bankingapp.data.remote.service.PaymentApiService
import com.example.bankingapp.data.remote.util.HandleResponse
import com.example.bankingapp.domain.model.PaymentDomain
import com.example.bankingapp.domain.repository.GetPaymentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetPaymentRepositoryImpl @Inject constructor(
    private val service: PaymentApiService,
    private val handler: HandleResponse
) : GetPaymentRepository {

    override suspend fun getPayments(): Flow<Resource<List<PaymentDomain>>> {
        return handler.safeApiCall { service.getPayments() }.mapListToDomain { it.toDomain() }
    }
}
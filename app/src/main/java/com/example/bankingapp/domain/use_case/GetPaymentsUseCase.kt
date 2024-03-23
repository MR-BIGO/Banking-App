package com.example.bankingapp.domain.use_case

import com.example.bankingapp.data.common.Resource
import com.example.bankingapp.domain.model.PaymentDomain
import com.example.bankingapp.domain.repository.GetPaymentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetPaymentsUseCase @Inject constructor(private val repository: GetPaymentRepository) {

    suspend operator fun invoke(): Flow<Resource<List<PaymentDomain>>> {
        return repository.getPayments()
    }
}
package com.example.bankingapp.data.remote.service

import com.example.bankingapp.data.remote.model.PaymentDto
import retrofit2.Response
import retrofit2.http.GET


interface PaymentApiService {
    @GET("252938ad-c468-4810-b9fa-564111e9643d")
    suspend fun getPayments(): Response<List<PaymentDto>>
}
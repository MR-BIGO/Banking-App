package com.example.bankingapp.data.remote.model

data class TransactionDto(
    val amount: Double = 0.0,
    val currency: String = "",
    val id: String = "",
    val merchant: String = "",
)
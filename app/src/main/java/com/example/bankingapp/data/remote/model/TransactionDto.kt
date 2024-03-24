package com.example.bankingapp.data.remote.model

import java.util.UUID

data class TransactionDto (
    val id: String,
    val merchant: String,
    val amount: Double,
    val currency: String
)
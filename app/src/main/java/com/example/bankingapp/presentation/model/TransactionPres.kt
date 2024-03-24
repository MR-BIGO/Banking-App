package com.example.bankingapp.presentation.model

import java.util.UUID

data class TransactionPres(
    val id: String = UUID.randomUUID().toString(),
    val merchant: String,
    val amount: Double,
    val currency: String
)

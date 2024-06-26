package com.example.bankingapp.domain.model

import java.util.UUID

data class TransactionDomain(
    val id: String,
    val merchant: String,
    val amount: Double,
    val currency: String
)

package com.example.bankingapp.domain.model

import java.util.UUID

data class TransactionDomain(
    val id: String = UUID.randomUUID().toString(),
    val merchant: String,
    val amount: Double
)

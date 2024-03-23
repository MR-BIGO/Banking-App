package com.example.bankingapp.presentation.model

import java.util.UUID

data class CardPres(
    val id: String = UUID.randomUUID().toString(),
    val cardNum: String,
    val cvv: String,
    val validDate: String,
    val amountGEL: Double,
    val amountUSD: Double,
    val amountEUR: Double
)

package com.example.bankingapp.domain.model

data class CardDomain(
    val id: String,
    val cardNum: String,
    val cvv: String,
    val validDate: String,
    val amountGEL: Double,
    val amountUSD: Double,
    val amountEUR: Double
)

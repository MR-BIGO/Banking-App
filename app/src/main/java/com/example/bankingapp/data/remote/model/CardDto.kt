package com.example.bankingapp.data.remote.model

data class CardDto (
    val id: String,
    val cardNum: String,
    val cvv: String,
    val validDate: String,
    val amountGEL: Double,
    val amountUSD: Double,
    val amountEUR: Double
)
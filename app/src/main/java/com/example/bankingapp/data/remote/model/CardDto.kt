package com.example.bankingapp.data.remote.model

data class CardDto (
    val amountEUR: Double = 0.00,
    val amountGEL: Double = 0.00,
    val amountUSD: Double = 0.00,
    val cardNum: String = "",
    val cvv: String = "",
    val id: String = "",
    val validDate: String = "",
)
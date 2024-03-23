package com.example.bankingapp.presentation.model

data class PaymentPres(
    val id: Int,
    val name: String,
    val type: CategoryType,
){
    enum class CategoryType {
        University,
        Delivery,
        Utilities,
        bet
    }
}


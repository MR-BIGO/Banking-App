package com.example.bankingapp.domain.model


data class PaymentDomain(
    val id: Int,
    val name: String,
    val type: CategoryType,
) {
    enum class CategoryType {
        University,
        Delivery,
        Utilities,
        bet
    }
}

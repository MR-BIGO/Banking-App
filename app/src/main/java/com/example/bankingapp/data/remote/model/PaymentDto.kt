package com.example.bankingapp.data.remote.model

import com.squareup.moshi.Json

data class PaymentDto(
    val id: Int,
    val name: String,
    @Json(name = "type")
    val type: CategoryType,
) {
    enum class CategoryType {
        University,
        Delivery,
        Utilities,
        bet
    }
}



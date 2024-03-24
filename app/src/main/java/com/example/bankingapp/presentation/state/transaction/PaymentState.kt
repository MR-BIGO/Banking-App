package com.example.bankingapp.presentation.state.transaction

import com.example.bankingapp.presentation.model.PaymentPres

data class PaymentState(
    val loading: Boolean = false,
    val payments: List<PaymentPres>? = null,
    val error: String? = null
)
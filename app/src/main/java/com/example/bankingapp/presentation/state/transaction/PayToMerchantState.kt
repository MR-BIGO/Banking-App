package com.example.bankingapp.presentation.state.transaction

data class PayToMerchantState(
    val loading: Boolean = false,
    val transactionSuccess: Boolean = false,
    val error: String? = null
)

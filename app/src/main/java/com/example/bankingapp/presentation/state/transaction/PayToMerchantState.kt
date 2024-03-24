package com.example.bankingapp.presentation.state.transaction

import com.example.bankingapp.presentation.model.CardPres

data class PayToMerchantState(
    val loading: Boolean = false,
    val error: String? = null,
    val chosenCard: CardPres? = null,
    val successTransaction: Boolean = false,
    val successCard: Boolean = false
)

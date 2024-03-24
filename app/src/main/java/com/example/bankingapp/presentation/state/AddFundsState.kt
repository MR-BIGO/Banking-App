package com.example.bankingapp.presentation.state

import com.example.bankingapp.presentation.model.CardPres

data class AddFundsState(
    val card: CardPres? = null,
    val error: String? = null,
    val loading: Boolean = false,
    val updateSuccess: Boolean = false,
    var updatedCard: CardPres? = null
)

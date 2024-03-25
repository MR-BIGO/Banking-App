package com.example.bankingapp.presentation.state.transaction

import com.example.bankingapp.presentation.model.CardPres

data class TransferToElseState (
    val loading: Boolean = false,
    val error: String? = null,
    val chosenCardFrom: CardPres? = null,
    val successTransaction: Boolean = false,
    val successCardFrom: Boolean = false,
)
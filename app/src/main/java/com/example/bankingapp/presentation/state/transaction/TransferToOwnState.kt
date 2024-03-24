package com.example.bankingapp.presentation.state.transaction

import com.example.bankingapp.presentation.model.CardPres

data class TransferToOwnState(
    val loading: Boolean = false,
    val error: String? = null,
    val chosenCardFrom: CardPres? = null,
    val chosenCardTo: CardPres? = null,
    val successTransaction: Boolean = false,
    val successCardFrom: Boolean = false,
    val successCardTo: Boolean = false
)

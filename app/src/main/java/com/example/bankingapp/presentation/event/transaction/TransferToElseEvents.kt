package com.example.bankingapp.presentation.event.transaction

import com.example.bankingapp.presentation.model.CardPres

sealed class TransferToElseEvents {
    data class TransferPressed(val amount: Double, val currency: String, val cardFrom: CardPres) :
        TransferToElseEvents()

    data object ResetError : TransferToElseEvents()
    data class ChosenCardFrom(val card: CardPres) : TransferToElseEvents()
}

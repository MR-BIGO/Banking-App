package com.example.bankingapp.presentation.event.transaction

import com.example.bankingapp.presentation.model.CardPres

sealed class TransferOwnEvents {
    data class TransferPressed(
        val amount: Double,
        val currency: String,
        val cardFrom: CardPres,
        val cardTo: CardPres
    ): TransferOwnEvents()

    data object ResetError: TransferOwnEvents()
    data class ChosenCardFrom(val card: CardPres): TransferOwnEvents()
    data class ChosenCardTo(val card: CardPres): TransferOwnEvents()

}

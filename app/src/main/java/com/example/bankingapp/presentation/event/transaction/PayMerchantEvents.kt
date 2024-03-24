package com.example.bankingapp.presentation.event.transaction

import com.example.bankingapp.presentation.model.CardPres

sealed class PayMerchantEvents {
    data class PayPressed(val name: String, val amount: Double, val currency: String, val card: CardPres) :
        PayMerchantEvents()

    data object ResetError: PayMerchantEvents()
    data class ChosenCard(val card: CardPres): PayMerchantEvents()
}

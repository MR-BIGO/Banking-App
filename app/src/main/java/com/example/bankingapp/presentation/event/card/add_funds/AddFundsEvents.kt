package com.example.bankingapp.presentation.event.card.add_funds

sealed class AddFundsEvents {
    data object ResetError : AddFundsEvents()
    data class FillBalancePressed(
        val newGel: Double,
        val newUsd: Double,
        val newEuro: Double
    ) : AddFundsEvents()

    data class GetCardInfo(val id: String) : AddFundsEvents()

    data object ResetSuccess: AddFundsEvents()
}

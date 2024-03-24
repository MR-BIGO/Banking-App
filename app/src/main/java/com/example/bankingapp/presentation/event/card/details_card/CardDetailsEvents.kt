package com.example.bankingapp.presentation.event.card.details_card

sealed class CardDetailsEvents {
    data class GetCardDetails(val id: String) : CardDetailsEvents()
    data class DeleteCardPressed(val id: String) : CardDetailsEvents()
    data class ShowDetailsPressed(val cardNum: String, val validDate: String, val cvv: String) :
        CardDetailsEvents()

    data object TransferToOwnPressed : CardDetailsEvents()
    data object TransferToElsePressed : CardDetailsEvents()
    data class AddFundsPressed(val id: String): CardDetailsEvents()

    data object ResetError: CardDetailsEvents()
}

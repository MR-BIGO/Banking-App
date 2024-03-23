package com.example.bankingapp.presentation.event.card.details_card

sealed class CardDetailsEvents{
    data class GetCardDetails(val id: String): CardDetailsEvents()
}

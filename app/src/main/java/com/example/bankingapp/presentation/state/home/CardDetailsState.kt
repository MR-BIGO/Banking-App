package com.example.bankingapp.presentation.state.home

import com.example.bankingapp.presentation.model.CardPres

data class CardDetailsState (
    val card: CardPres? = null,
    val error: String? = null,
    val loading: Boolean = false,
    val deleteSuccess: Boolean = false
)
package com.example.bankingapp.presentation.state.transaction

import com.example.bankingapp.presentation.model.CardPres

data class CardsBottomSheetState(
    val error: String? = null,
    val loading: Boolean = false,
    val cards: List<CardPres>? = null
)

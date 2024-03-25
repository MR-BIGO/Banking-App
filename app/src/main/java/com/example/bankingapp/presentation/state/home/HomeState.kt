package com.example.bankingapp.presentation.state.home

import com.example.bankingapp.presentation.model.CardPres
import com.example.bankingapp.presentation.model.StoryPres
import com.example.bankingapp.presentation.model.TransactionPres

data class HomeState (
    val loading: Boolean = false,
    val stories: List<StoryPres>? = null,
    val cards: List<CardPres>? = null,
    val transactions: List<TransactionPres>? = null,
    val error: String? = null
)
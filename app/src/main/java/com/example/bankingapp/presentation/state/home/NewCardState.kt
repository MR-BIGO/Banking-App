package com.example.bankingapp.presentation.state.home

data class NewCardState(
    val successMessage: String? = null,
    val error: String? = null,
    val loading: Boolean = false
)

package com.example.bankingapp.presentation.event

import android.text.Editable

sealed class NewCardEvents {
    data class CardNumberChanged(val number: Editable): NewCardEvents()
    data class CardDateChanged(val date: Editable): NewCardEvents()
    data class CardCvvChanged(val cvv: Editable): NewCardEvents()
    data class AddCard(val cardNum: String, val cardDate: String, val cardCvv: String): NewCardEvents()
    data object ResetError: NewCardEvents()
}
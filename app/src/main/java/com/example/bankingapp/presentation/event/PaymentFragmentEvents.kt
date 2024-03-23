package com.example.bankingapp.presentation.event


sealed class PaymentFragmentEvents {
    data object ResetError : PaymentFragmentEvents()
    data object GetPayments : PaymentFragmentEvents()
    data class PaymentPressed(val name: String) : PaymentFragmentEvents()
}

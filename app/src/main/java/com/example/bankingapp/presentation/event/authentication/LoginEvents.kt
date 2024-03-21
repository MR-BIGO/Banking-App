package com.example.bankingapp.presentation.event.authentication

sealed class LoginEvents {
    data class LoginPressed(val email: String, val password: String) : LoginEvents()
    data object ResetError : LoginEvents()
}

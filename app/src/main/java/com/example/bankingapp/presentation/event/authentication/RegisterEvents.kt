package com.example.bankingapp.presentation.event.authentication

sealed class RegisterEvents {
    data class RegisterPressed(
        val email: String,
        val password: String,
        val repeatPassword: String
    ) : RegisterEvents()

    data object AlreadyAccountPressed: RegisterEvents()

    data object ResetError : RegisterEvents()
}

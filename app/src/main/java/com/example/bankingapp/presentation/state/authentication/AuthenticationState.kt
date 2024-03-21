package com.example.bankingapp.presentation.state.authentication

import com.example.bankingapp.presentation.model.UserPres

data class AuthenticationState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSuccessful: Boolean = false,
    val user: UserPres = UserPres("", "")
)

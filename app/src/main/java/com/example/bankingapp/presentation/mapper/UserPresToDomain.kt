package com.example.bankingapp.presentation.mapper

import com.example.bankingapp.domain.model.User
import com.example.bankingapp.presentation.model.UserPres

fun UserPres.toDomain(): User {
    return User(
        email = email,
        password = password
    )
}
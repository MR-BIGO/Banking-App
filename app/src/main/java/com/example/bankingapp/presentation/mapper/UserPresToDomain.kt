package com.example.bankingapp.presentation.mapper

import com.example.bankingapp.domain.model.UserDomain
import com.example.bankingapp.presentation.model.UserPres

fun UserPres.toDomain(): UserDomain {
    return UserDomain(
        email = email,
        password = password
    )
}
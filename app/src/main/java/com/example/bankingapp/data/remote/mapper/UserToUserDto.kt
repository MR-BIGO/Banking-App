package com.example.bankingapp.data.remote.mapper

import com.example.bankingapp.data.remote.model.UserDto
import com.example.bankingapp.domain.model.UserDomain

fun UserDomain.toDto(): UserDto {
    return UserDto(
        email = email,
        password = password
    )
}
package com.example.bankingapp.data.remote.mapper

import com.example.bankingapp.data.remote.model.UserDto
import com.example.bankingapp.domain.model.User

fun User.toDto(): UserDto {
    return UserDto(
        email = email,
        password = password
    )
}
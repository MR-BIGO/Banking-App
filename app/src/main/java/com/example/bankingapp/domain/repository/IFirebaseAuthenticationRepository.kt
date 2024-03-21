package com.example.bankingapp.domain.repository

import com.example.bankingapp.data.common.Resource
import com.example.bankingapp.domain.model.User
import kotlinx.coroutines.flow.Flow

interface IFirebaseAuthenticationRepository {
    suspend fun loginUser(user: User): Flow<Resource<Boolean>>
    suspend fun registerUser(user: User): Flow<Resource<Boolean>>
    fun logOutUser()
}
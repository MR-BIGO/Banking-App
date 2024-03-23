package com.example.bankingapp.domain.repository

import com.example.bankingapp.data.common.Resource
import com.example.bankingapp.domain.model.UserDomain
import kotlinx.coroutines.flow.Flow

interface IFirebaseAuthenticationRepository {
    suspend fun loginUser(user: UserDomain): Flow<Resource<Boolean>>
    suspend fun registerUser(user: UserDomain): Flow<Resource<Boolean>>
    fun logOutUser()
}
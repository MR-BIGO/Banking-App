package com.example.bankingapp.data.remote.util

import com.example.bankingapp.data.common.Resource
import com.example.bankingapp.data.remote.model.UserDto
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class HandleAuth {
    suspend fun safeAuthentication(
        user: UserDto,
        operation: (String, String) -> Task<AuthResult>,
        onSuccess: (AuthResult) -> Boolean
    ): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading(true))
        try {
            val result = operation(user.email, user.password).await()
            if (result.user != null) {
                emit(Resource.Success(onSuccess(result)))
            } else {
                emit(Resource.Error("Authentication failed"))
            }
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            emit(Resource.Error(e.message ?: "Unknown exception"))
        } catch (e: FirebaseAuthInvalidUserException) {
            emit(Resource.Error(e.message ?: "User not found"))
        } catch (e: FirebaseAuthUserCollisionException) {
            emit(Resource.Error(e.message ?: "User already exists"))
        } catch (e: Throwable) {
            emit(Resource.Error(e.message ?: "Unknown Error"))
        }
        emit(Resource.Loading(false))
    }
}
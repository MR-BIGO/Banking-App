package com.example.bankingapp.data.repository.remote

import com.example.bankingapp.data.common.Resource
import com.example.bankingapp.data.remote.mapper.toDto
import com.example.bankingapp.data.remote.util.HandleAuth
import com.example.bankingapp.domain.model.UserDomain
import com.example.bankingapp.domain.repository.IFirebaseAuthenticationRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FirebaseAuthenticationRepositoryImpl @Inject constructor(
    private val safeAuth: HandleAuth,
    private val firebase: FirebaseAuth
) : IFirebaseAuthenticationRepository {
    override suspend fun loginUser(user: UserDomain): Flow<Resource<Boolean>> {
        return safeAuth.safeAuthentication(
            user.toDto(),
            firebase::signInWithEmailAndPassword,
            onSuccess = { true })
    }

    override suspend fun registerUser(user: UserDomain): Flow<Resource<Boolean>> {
        return safeAuth.safeAuthentication(
            user.toDto(),
            firebase::createUserWithEmailAndPassword,
            onSuccess = { true })
    }

    override fun logOutUser() {
        firebase.signOut()
    }
}
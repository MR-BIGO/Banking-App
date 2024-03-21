package com.example.bankingapp.di

import com.example.bankingapp.data.remote.util.HandleAuth
import com.example.bankingapp.data.repository.remote.FirebaseAuthenticationRepositoryImpl
import com.example.bankingapp.domain.repository.IFirebaseAuthenticationRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideFirebaseAuthenticationRepository(
        handleAuth: HandleAuth,
        firebaseAuth: FirebaseAuth
    ): IFirebaseAuthenticationRepository {
        return FirebaseAuthenticationRepositoryImpl(handleAuth, firebaseAuth)
    }

    @Singleton
    @Provides
    fun provideHandleAuth(): HandleAuth = HandleAuth()
}
package com.example.bankingapp.di

import com.example.bankingapp.data.remote.service.PaymentApiService
import com.example.bankingapp.data.remote.service.StoriesApiService
import com.example.bankingapp.data.remote.util.HandleAuth
import com.example.bankingapp.data.remote.util.HandleResponse
import com.example.bankingapp.data.repository.remote.CardsRepositoryImpl
import com.example.bankingapp.data.repository.remote.FirebaseAuthenticationRepositoryImpl
import com.example.bankingapp.data.repository.remote.GetPaymentRepositoryImpl
import com.example.bankingapp.data.repository.remote.StoriesRepositoryImpl
import com.example.bankingapp.domain.repository.GetPaymentRepository
import com.example.bankingapp.domain.repository.ICardsRepository
import com.example.bankingapp.domain.repository.IFirebaseAuthenticationRepository
import com.example.bankingapp.domain.repository.StoriesRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
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
    fun provideCardsRepository(firebaseDb: DatabaseReference, firebaseAuth: FirebaseAuth): ICardsRepository{
        return CardsRepositoryImpl(firebaseDb, firebaseAuth)
    }

    @Singleton
    @Provides
    fun providePaymentRepository(service: PaymentApiService, handler: HandleResponse): GetPaymentRepository{
        return GetPaymentRepositoryImpl(service,handler)
    }

    @Singleton
    @Provides
    fun provideHandleAuth(): HandleAuth = HandleAuth()

    @Provides
    @Singleton
    fun provideHandleResponse(): HandleResponse {
        return HandleResponse()
    }

    @Provides
    @Singleton
    fun provideStoriesRepository(
        service: StoriesApiService,
        handler: HandleResponse
    ): StoriesRepository {
        return StoriesRepositoryImpl(service, handler)
    }

}
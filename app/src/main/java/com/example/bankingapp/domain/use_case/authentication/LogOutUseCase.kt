package com.example.bankingapp.domain.use_case.authentication

import com.example.bankingapp.domain.repository.IFirebaseAuthenticationRepository
import javax.inject.Inject

class LogOutUseCase @Inject constructor(
    private val repository: IFirebaseAuthenticationRepository
) {
    operator fun invoke(){
        repository.logOutUser()
    }
}
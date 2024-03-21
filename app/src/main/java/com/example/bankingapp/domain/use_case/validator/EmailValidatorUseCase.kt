package com.example.bankingapp.domain.use_case.validator

import javax.inject.Inject

class EmailValidatorUseCase @Inject constructor() {
    operator fun invoke(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email)
            .matches()
    }
}
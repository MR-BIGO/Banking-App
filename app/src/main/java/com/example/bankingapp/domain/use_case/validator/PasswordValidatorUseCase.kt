package com.example.bankingapp.domain.use_case.validator

import javax.inject.Inject

class PasswordValidatorUseCase @Inject constructor(){
    operator fun invoke(password: String): Boolean = password.length > 8 && password.contains("[0-9]".toRegex())
}
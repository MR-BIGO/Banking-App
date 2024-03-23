package com.example.bankingapp.domain.use_case.authentication

import com.example.bankingapp.data.common.Resource
import com.example.bankingapp.domain.model.UserDomain
import com.example.bankingapp.domain.repository.IFirebaseAuthenticationRepository
import com.example.bankingapp.domain.use_case.validator.EmailValidatorUseCase
import com.example.bankingapp.domain.use_case.validator.PasswordValidatorUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val repository: IFirebaseAuthenticationRepository,
    private val validateEmail: EmailValidatorUseCase,
    private val validatePassword: PasswordValidatorUseCase
) {
    suspend operator fun invoke(user: UserDomain): Flow<Resource<Boolean>> {
        if (!validateEmail(user.email)) return flowOf(Resource.Error("Please, Enter a valid Email address"))

        else if (!validatePassword(user.password)) return flowOf(Resource.Error("Password should contain numbers and should be longer than 8 letters!"))

        return repository.loginUser(user)
    }
}
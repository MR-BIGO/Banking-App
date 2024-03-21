package com.example.bankingapp.presentation.screen.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankingapp.data.common.Resource
import com.example.bankingapp.domain.use_case.authentication.RegisterUserUseCase
import com.example.bankingapp.presentation.event.authentication.RegisterEvents
import com.example.bankingapp.presentation.mapper.toDomain
import com.example.bankingapp.presentation.model.UserPres
import com.example.bankingapp.presentation.state.authentication.AuthenticationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterFragmentViewModel @Inject constructor(private val registerUserUseCase: RegisterUserUseCase) :
    ViewModel() {
    private val _registerState = MutableStateFlow(AuthenticationState())
    val registerState: SharedFlow<AuthenticationState> = _registerState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<RegisterNavigationEvents>()
    val uiEvent: SharedFlow<RegisterNavigationEvents> get() = _uiEvent

    fun onEvent(event: RegisterEvents) {
        when (event) {
            is RegisterEvents.RegisterPressed -> {
                saveUser(event.email, event.password)
                register(event.email, event.password, event.repeatPassword)
            }

            is RegisterEvents.ResetError -> {
                updateError(null)
            }

            is RegisterEvents.AlreadyAccountPressed -> {
                handleAlreadyAccountPressed()
            }
        }
    }

    private fun register(email: String, password: String, repeatPassword: String) {
        viewModelScope.launch {
            registerUserUseCase(UserPres(email, password).toDomain(), repeatPassword).collect {
                handleResult(it)
            }
        }
    }

    private fun handleResult(result: Resource<Boolean>) {
        viewModelScope.launch {
            when (result) {
                is Resource.Success -> {
                    _uiEvent.emit(
                        RegisterNavigationEvents.NavigateToLogin(
                            _registerState.value.user.email,
                            _registerState.value.user.password
                        )
                    )
                }

                is Resource.Error -> {
                    updateError(result.error)
                }

                is Resource.Loading -> {
                    updateLoading(result.loading)
                }
            }
        }
    }

    private fun updateLoading(loading: Boolean) {
        _registerState.update { currentState -> currentState.copy(isLoading = loading) }
    }

    private fun updateError(error: String?) {
        _registerState.update { currentState -> currentState.copy(errorMessage = error) }
    }

    private fun saveUser(email: String, password: String) {
        _registerState.update { currentState ->
            currentState.copy(
                user = UserPres(
                    email,
                    password
                )
            )
        }
    }

    private fun handleAlreadyAccountPressed() {
        viewModelScope.launch {
            _uiEvent.emit(RegisterNavigationEvents.NavigateToLogin(null, null))
        }
    }

    sealed class RegisterNavigationEvents {
        data class NavigateToLogin(val email: String?, val password: String?) :
            RegisterNavigationEvents()
    }
}
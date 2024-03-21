package com.example.bankingapp.presentation.screen.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankingapp.data.common.Resource
import com.example.bankingapp.domain.use_case.authentication.LoginUserUseCase
import com.example.bankingapp.presentation.event.authentication.LoginEvents
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
class LogInFragmentViewModel @Inject constructor(private val loginUserUseCase: LoginUserUseCase) :
    ViewModel() {
    private val _loginState = MutableStateFlow(AuthenticationState())
    val loginState: SharedFlow<AuthenticationState> = _loginState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<LoginNavigationEvents>()
    val uiEvent: SharedFlow<LoginNavigationEvents> get() = _uiEvent

    fun onEvent(event: LoginEvents) {
        when (event) {
            is LoginEvents.LoginPressed -> {
                logIn(event.email, event.password)
            }

            is LoginEvents.ResetError -> updateError(null)
        }
    }

    private fun logIn(email: String, password: String) {
        viewModelScope.launch {
            loginUserUseCase(UserPres(email, password).toDomain()).collect {
                handleResult(it)
            }
        }
    }

    private fun handleResult(result: Resource<Boolean>) {
        viewModelScope.launch {
            when (result) {
                is Resource.Success -> {
                    _uiEvent.emit(LoginNavigationEvents.NavigateToHome)
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
        _loginState.update { currentState -> currentState.copy(isLoading = loading) }
    }

    private fun updateError(error: String?) {
        _loginState.update { currentState -> currentState.copy(errorMessage = error) }
    }

    sealed class LoginNavigationEvents {
        data object NavigateToHome : LoginNavigationEvents()
    }
}
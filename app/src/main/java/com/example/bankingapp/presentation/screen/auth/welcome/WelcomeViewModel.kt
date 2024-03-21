package com.example.bankingapp.presentation.screen.auth.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
  //  private val isUserLoggedInUseCase: isUserLoggedInUseCase
) : ViewModel() {
    val isUserLoggedIn = MutableSharedFlow<Boolean>()

//    init {
//        isUserLoggedIn()
//    }
//
//    private fun isUserLoggedIn() {
//        viewModelScope.launch {
//            isUserLoggedIn.emit(isUserLoggedInUseCase())
//        }
//    }
}
package com.example.bankingapp.presentation.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankingapp.domain.use_case.authentication.LogOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileFragmentViewModel @Inject constructor(
    private val logOutUseCase: LogOutUseCase,
) : ViewModel() {

    fun logOut() {
        viewModelScope.launch {
            logOutUseCase()
        }
    }
}
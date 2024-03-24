package com.example.bankingapp.presentation.screen.transactions.payment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankingapp.data.common.Resource
import com.example.bankingapp.domain.use_case.GetPaymentsUseCase
import com.example.bankingapp.presentation.event.PaymentFragmentEvents
import com.example.bankingapp.presentation.mapper.toPresentation
import com.example.bankingapp.presentation.state.transaction.PaymentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentFragmentViewModel @Inject constructor(
    private val getPaymentsUseCase: GetPaymentsUseCase
) : ViewModel() {

    private val _paymentState = MutableStateFlow(PaymentState())
    val paymentState: StateFlow<PaymentState> = _paymentState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<PaymentsNavigationEvents>()
    val uiEvent: SharedFlow<PaymentsNavigationEvents> get() = _uiEvent


    fun onEvent(event: PaymentFragmentEvents) {
        when (event) {
            is PaymentFragmentEvents.GetPayments -> getPayments()
            is PaymentFragmentEvents.PaymentPressed -> {}
            is PaymentFragmentEvents.ResetError -> setError(null)
        }
    }

    private fun setError(error: String?) {
        _paymentState.update { currentState -> currentState.copy(error = error) }
    }

    private fun setLoading(loading: Boolean) {
        _paymentState.update { currentState -> currentState.copy(loading = loading) }
    }

    private fun getPayments() {
        viewModelScope.launch {
            getPaymentsUseCase().collect {
                when (it) {
                    is Resource.Success -> {
                        _paymentState.update { currentState -> currentState.copy(payments = it.data.map { payments -> payments.toPresentation() }) }
                    }

                    is Resource.Error -> {
                        setError(it.error)
                    }

                    is Resource.Loading -> {
                        setLoading(it.loading)
                    }
                }
            }
        }
    }

    sealed class PaymentsNavigationEvents {
        data class NavigateToMerchantPayment(val name: String) : PaymentsNavigationEvents()
    }
}
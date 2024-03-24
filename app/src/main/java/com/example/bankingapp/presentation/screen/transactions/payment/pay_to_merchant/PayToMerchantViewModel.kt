package com.example.bankingapp.presentation.screen.transactions.payment.pay_to_merchant

import androidx.lifecycle.ViewModel
import com.example.bankingapp.presentation.event.transaction.PayMerchantEvents
import com.example.bankingapp.presentation.state.transaction.PayToMerchantState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PayToMerchantViewModel @Inject constructor() : ViewModel() {

    private val _payMerchantState = MutableStateFlow(PayToMerchantState())
    val payMerchantState: StateFlow<PayToMerchantState> = _payMerchantState.asStateFlow()

    fun onEvent(event: PayMerchantEvents){
        when(event){
            is PayMerchantEvents.PayPressed -> {}
            is PayMerchantEvents.ResetError -> {}
            is PayMerchantEvents.ResetSuccess -> {}
        }
    }

    private fun setError(error: String?){

    }

    private fun setLoading(loading: Boolean){

    }

    private fun setSuccess(result: Boolean){

    }
}
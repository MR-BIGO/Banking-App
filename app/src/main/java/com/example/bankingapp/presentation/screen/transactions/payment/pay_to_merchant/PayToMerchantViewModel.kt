package com.example.bankingapp.presentation.screen.transactions.payment.pay_to_merchant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankingapp.data.common.Resource
import com.example.bankingapp.domain.use_case.card_oriented.UpdateCardUseCase
import com.example.bankingapp.domain.use_case.transaction.SaveTransactionUseCase
import com.example.bankingapp.presentation.event.transaction.PayMerchantEvents
import com.example.bankingapp.presentation.mapper.toDomain
import com.example.bankingapp.presentation.model.CardPres
import com.example.bankingapp.presentation.model.TransactionPres
import com.example.bankingapp.presentation.state.transaction.PayToMerchantState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PayToMerchantViewModel @Inject constructor(
    private val updateCardUseCase: UpdateCardUseCase,
    private val saveTransactionUseCase: SaveTransactionUseCase
) : ViewModel() {

    private val _payMerchantState = MutableStateFlow(PayToMerchantState())
    val payMerchantState: StateFlow<PayToMerchantState> = _payMerchantState.asStateFlow()

    fun onEvent(event: PayMerchantEvents) {
        when (event) {
            is PayMerchantEvents.PayPressed -> {
                handlePayPressed(event.name, event.amount, event.currency, event.card)
            }

            is PayMerchantEvents.ResetError -> {
                setError(null)
            }

            is PayMerchantEvents.ChosenCard -> {
                _payMerchantState.update { currentState -> currentState.copy(chosenCard = event.card) }
            }
        }
    }

    private fun handlePayPressed(name: String, amount: Double, currency: String, card: CardPres) {
        viewModelScope.launch {
            saveTransactionUseCase(
                TransactionPres(
                    merchant = name,
                    amount = amount,
                    currency = currency
                ).toDomain()
            ).collect {
                when (it) {
                    is Resource.Error -> setError(it.error)
                    is Resource.Loading -> setLoading(it.loading)
                    is Resource.Success -> {
                        _payMerchantState.update { currentState ->
                            currentState.copy(
                                successTransaction = it.data
                            )
                        }
                    }
                }
            }
            updateCardUseCase(
                _payMerchantState.value.chosenCard!!.copy(amountGEL = card.amountGEL - amount)
                    .toDomain()
            ).collect {
                when (it) {
                    is Resource.Error -> setError(it.error)
                    is Resource.Loading -> setLoading(it.loading)
                    is Resource.Success -> {
                        _payMerchantState.update { currentState -> currentState.copy(successCard = it.data) }
                    }
                }
            }
        }
    }

    private fun setError(error: String?) {
        _payMerchantState.update { currentState -> currentState.copy(error = error) }
    }

    private fun setLoading(loading: Boolean) {
        _payMerchantState.update { currentState -> currentState.copy(loading = loading) }
    }

}
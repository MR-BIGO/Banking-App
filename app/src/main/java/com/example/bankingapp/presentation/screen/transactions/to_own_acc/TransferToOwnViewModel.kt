package com.example.bankingapp.presentation.screen.transactions.to_own_acc

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankingapp.data.common.Resource
import com.example.bankingapp.domain.use_case.card_oriented.UpdateCardUseCase
import com.example.bankingapp.domain.use_case.transaction.SaveTransactionUseCase
import com.example.bankingapp.presentation.event.transaction.TransferOwnEvents
import com.example.bankingapp.presentation.mapper.toDomain
import com.example.bankingapp.presentation.model.CardPres
import com.example.bankingapp.presentation.model.TransactionPres
import com.example.bankingapp.presentation.state.transaction.TransferToOwnState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransferToOwnViewModel @Inject constructor(
    private val updateCardUseCase: UpdateCardUseCase,
    private val saveTransactionUseCase: SaveTransactionUseCase
) : ViewModel() {

    private val _transferOwnState = MutableStateFlow(TransferToOwnState())
    val transferOwnState: StateFlow<TransferToOwnState> = _transferOwnState.asStateFlow()

    fun onEvent(event: TransferOwnEvents) {
        when (event) {
            is TransferOwnEvents.ChosenCardFrom -> {
                _transferOwnState.update { currentState -> currentState.copy(chosenCardFrom = event.card) }

            }

            is TransferOwnEvents.ChosenCardTo -> {
                _transferOwnState.update { currentState -> currentState.copy(chosenCardTo = event.card) }
            }

            is TransferOwnEvents.ResetError -> {
                setError(null)
            }

            is TransferOwnEvents.TransferPressed -> {
                handleTransferPressed(event.amount, event.currency, event.cardFrom, event.cardTo)
            }
        }
    }

    private fun handleTransferPressed(
        amount: Double,
        currency: String,
        cardFrom: CardPres,
        cardTo: CardPres
    ) {
        viewModelScope.launch {
            saveTransactionUseCase(
                TransactionPres(
                    merchant = "Transfer To Own",
                    amount = amount,
                    currency = currency
                ).toDomain()
            ).collect {
                when (it) {
                    is Resource.Error -> setError(it.error)
                    is Resource.Loading -> setLoading(it.loading)
                    is Resource.Success -> {
                        _transferOwnState.update { currentState ->
                            currentState.copy(
                                successTransaction = it.data
                            )
                        }
                    }
                }
            }

            updateCardUseCase(
                _transferOwnState.value.chosenCardFrom!!.copy(amountGEL = cardFrom.amountGEL - amount)
                    .toDomain()
            ).collect {
                when (it) {
                    is Resource.Error -> setError(it.error)
                    is Resource.Loading -> setLoading(it.loading)
                    is Resource.Success -> {
                        _transferOwnState.update { currentState -> currentState.copy(successCardFrom = it.data) }
                    }
                }
            }

            updateCardUseCase(
                _transferOwnState.value.chosenCardTo!!.copy(amountGEL = cardFrom.amountGEL + amount)
                    .toDomain()
            ).collect {
                when (it) {
                    is Resource.Error -> setError(it.error)
                    is Resource.Loading -> setLoading(it.loading)
                    is Resource.Success -> {
                        _transferOwnState.update { currentState -> currentState.copy(successCardTo = it.data) }
                    }
                }
            }
        }
    }

    private fun setError(error: String?) {
        _transferOwnState.update { currentState -> currentState.copy(error = error) }
    }

    private fun setLoading(loading: Boolean) {
        _transferOwnState.update { currentState -> currentState.copy(loading = loading) }
    }
}
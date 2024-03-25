package com.example.bankingapp.presentation.screen.transactions.to_someone_else

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankingapp.data.common.Resource
import com.example.bankingapp.domain.use_case.card_oriented.UpdateCardUseCase
import com.example.bankingapp.domain.use_case.transaction.SaveTransactionUseCase
import com.example.bankingapp.presentation.event.transaction.TransferToElseEvents
import com.example.bankingapp.presentation.mapper.toDomain
import com.example.bankingapp.presentation.model.CardPres
import com.example.bankingapp.presentation.model.TransactionPres
import com.example.bankingapp.presentation.state.transaction.TransferToElseState
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransferToElseViewModel @Inject constructor(
    private val updateCardUseCase: UpdateCardUseCase,
    private val saveTransactionUseCase: SaveTransactionUseCase
) : ViewModel() {

    private val _transferElseState = MutableStateFlow(TransferToElseState())
    val transferElseState: StateFlow<TransferToElseState> = _transferElseState.asStateFlow()

    fun onEvent(event: TransferToElseEvents) {
        when (event) {
            is TransferToElseEvents.ChosenCardFrom -> {
                _transferElseState.update { currentState -> currentState.copy(chosenCardFrom = event.card) }
            }
            is TransferToElseEvents.ResetError -> {
                setError(null)
            }

            is TransferToElseEvents.TransferPressed -> {
                handleTransferPressed(event.amount, event.currency, event.cardFrom)
            }
        }
    }

    private fun handleTransferPressed(
        amount: Double,
        currency: String,
        cardFrom: CardPres,
    ) {
        viewModelScope.launch {
            saveTransactionUseCase(
                TransactionPres(
                    merchant = "Transfer To Someone Else",
                    amount = amount,
                    currency = currency
                ).toDomain()
            ).collect {
                when (it) {
                    is Resource.Error -> setError(it.error)
                    is Resource.Loading -> setLoading(it.loading)
                    is Resource.Success -> {
                        _transferElseState.update { currentState ->
                            currentState.copy(
                                successTransaction = it.data
                            )
                        }
                    }
                }
            }
            updateCardUseCase(
                _transferElseState.value.chosenCardFrom!!.copy(amountGEL = cardFrom.amountGEL - amount)
                    .toDomain()
            ).collect {
                when (it) {
                    is Resource.Error -> setError(it.error)
                    is Resource.Loading -> setLoading(it.loading)
                    is Resource.Success -> {
                        _transferElseState.update { currentState ->
                            currentState.copy(
                                successCardFrom = it.data
                            )
                        }
                    }
                }
            }
        }
    }

    private fun setError(error: String?) {
        _transferElseState.update { currentState -> currentState.copy(error = error) }
    }

    private fun setLoading(loading: Boolean) {
        _transferElseState.update { currentState -> currentState.copy(loading = loading) }
    }
}
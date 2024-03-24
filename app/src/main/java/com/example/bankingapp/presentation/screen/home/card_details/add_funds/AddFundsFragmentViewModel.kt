package com.example.bankingapp.presentation.screen.home.card_details.add_funds

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankingapp.data.common.Resource
import com.example.bankingapp.domain.use_case.card_oriented.GetCardByIdUseCase
import com.example.bankingapp.domain.use_case.card_oriented.UpdateCardUseCase
import com.example.bankingapp.presentation.event.card.add_funds.AddFundsEvents
import com.example.bankingapp.presentation.mapper.toDomain
import com.example.bankingapp.presentation.mapper.toPres
import com.example.bankingapp.presentation.state.AddFundsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddFundsFragmentViewModel @Inject constructor(
    private val getCardByIdUseCase: GetCardByIdUseCase,
    private val updateCardUseCase: UpdateCardUseCase
) :
    ViewModel() {

    private val _addFundsState = MutableStateFlow(AddFundsState())
    val addFundsState: SharedFlow<AddFundsState> = _addFundsState.asStateFlow()

    fun onEvent(event: AddFundsEvents) {
        when (event) {
            is AddFundsEvents.FillBalancePressed -> {
                fillFunds(event.newGel, event.newUsd, event.newEuro)
            }

            is AddFundsEvents.ResetError -> {
                setError(null)
            }

            is AddFundsEvents.GetCardInfo -> {
                getCardInfo(event.id)
            }

            AddFundsEvents.ResetSuccess -> {
                setUpdateSuccess(false)
            }
        }
    }

    private fun getCardInfo(id: String) {
        viewModelScope.launch {
            getCardByIdUseCase(id).collect {
                when (it) {
                    is Resource.Error -> {
                        setError(it.error)
                    }

                    is Resource.Loading -> {
                        setLoading(it.loading)
                    }

                    is Resource.Success -> {
                        _addFundsState.update { currentState -> currentState.copy(card = it.data.toPres()) }
                    }
                }
            }
        }
    }

    private fun setUpdateSuccess(success: Boolean) {
        _addFundsState.update { currentState -> currentState.copy(updateSuccess = success) }
    }

    private fun fillFunds(newGel: Double, newUsd: Double, newEuro: Double) {
        viewModelScope.launch {
            _addFundsState.value.updatedCard = _addFundsState.value.card!!.copy(
                amountGEL = newGel,
                amountUSD = newUsd,
                amountEUR = newEuro
            )
            updateCardUseCase(_addFundsState.value.updatedCard!!.toDomain()).collect {
                when (it) {
                    is Resource.Error -> {
                        setError(it.error)
                    }

                    is Resource.Loading -> {
                        setLoading(it.loading)
                    }

                    is Resource.Success -> {
                        setUpdateSuccess(it.data)
                    }
                }
            }
        }
    }
    private fun setError(error: String?) {
        _addFundsState.update { currentState -> currentState.copy(error = error) }
    }

    private fun setLoading(loading: Boolean) {
        _addFundsState.update { currentState -> currentState.copy(loading = loading) }
    }
}
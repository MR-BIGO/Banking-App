package com.example.bankingapp.presentation.screen.home.card_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankingapp.data.common.Resource
import com.example.bankingapp.domain.model.CardDomain
import com.example.bankingapp.domain.use_case.card_oriented.DeleteCardUseCase
import com.example.bankingapp.domain.use_case.card_oriented.GetCardByIdUseCase
import com.example.bankingapp.presentation.event.card.details_card.CardDetailsEvents
import com.example.bankingapp.presentation.mapper.toPres
import com.example.bankingapp.presentation.state.CardDetailsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardDetailsViewModel @Inject constructor(
    private val getCardById: GetCardByIdUseCase,
    private val deleteCardUseCase: DeleteCardUseCase
) :
    ViewModel() {

    private val _cardDetailsState = MutableStateFlow(CardDetailsState())
    val cardDetailsState = _cardDetailsState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<DetailsNavigationEvent>()
    val uiEvent: SharedFlow<DetailsNavigationEvent> get() = _uiEvent

    fun onEvent(event: CardDetailsEvents) {
        when (event) {
            is CardDetailsEvents.GetCardDetails -> {
                getCard(event.id)
            }

            is CardDetailsEvents.DeleteCardPressed -> {deleteCard(event.id)}
            is CardDetailsEvents.ResetError -> {
                setError(null)
            }

            is CardDetailsEvents.ShowDetailsPressed -> {
                emitNavigationEvent(
                    DetailsNavigationEvent.NavigateToDetails(
                        event.cardNum,
                        event.validDate,
                        event.cvv
                    )
                )
            }

            is CardDetailsEvents.TransferToElsePressed -> {
                emitNavigationEvent(DetailsNavigationEvent.NavigateToTransferElse)
            }

            is CardDetailsEvents.TransferToOwnPressed -> {
                emitNavigationEvent(DetailsNavigationEvent.NavigateToTransferOwn)
            }

            is CardDetailsEvents.AddFundsPressed -> {
                emitNavigationEvent(DetailsNavigationEvent.NavigateToFunds(event.id))
            }
        }
    }

    private fun getCard(id: String) {
        viewModelScope.launch {
            getCardById(id).collect {
                handleResult(it)
            }
        }
    }

    private fun deleteCard(id: String) {
        viewModelScope.launch {
            deleteCardUseCase(id).collect {
                when (it) {
                    is Resource.Error -> {
                        setError(it.error)
                    }

                    is Resource.Loading -> {
                        setLoading(it.loading)
                    }

                    is Resource.Success -> {
                        _cardDetailsState.update { currentState -> currentState.copy(deleteSuccess = it.data) }
                    }
                }
            }
        }
    }

    private fun handleResult(result: Resource<CardDomain>) {
        when (result) {
            is Resource.Success -> {
                setSuccess(result.data)
            }

            is Resource.Error -> {
                setError(result.error)
            }

            is Resource.Loading -> {
                setLoading(result.loading)
            }
        }
    }

    private fun emitNavigationEvent(event: DetailsNavigationEvent) {
        viewModelScope.launch {
            _uiEvent.emit(event)
        }
    }

    private fun setSuccess(card: CardDomain) {
        _cardDetailsState.update { currentState -> currentState.copy(card = card.toPres()) }
    }

    private fun setError(error: String?) {
        _cardDetailsState.update { currentState -> currentState.copy(error = error) }
    }

    private fun setLoading(loading: Boolean) {
        _cardDetailsState.update { currentState -> currentState.copy(loading = loading) }
    }

    sealed class DetailsNavigationEvent {
        data class NavigateToDetails(val cardNum: String, val validDate: String, val cvv: String) :
            DetailsNavigationEvent()

        data object NavigateToTransferElse : DetailsNavigationEvent()
        data object NavigateToTransferOwn : DetailsNavigationEvent()
        data class NavigateToFunds(val id: String) : DetailsNavigationEvent()
    }
}
package com.example.bankingapp.presentation.screen.home.card_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankingapp.data.common.Resource
import com.example.bankingapp.domain.model.CardDomain
import com.example.bankingapp.domain.use_case.card_oriented.GetCardByIdUseCase
import com.example.bankingapp.presentation.event.card.details_card.CardDetailsEvents
import com.example.bankingapp.presentation.mapper.toPres
import com.example.bankingapp.presentation.state.CardDetailsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardDetailsViewModel @Inject constructor(private val getCardById: GetCardByIdUseCase) :
    ViewModel() {

    private val _cardDetailsState = MutableStateFlow(CardDetailsState())
    val cardDetailsState = _cardDetailsState.asStateFlow()

    fun onEvent(event: CardDetailsEvents) {
        when (event) {
            is CardDetailsEvents.GetCardDetails -> {
                getCard(event.id)
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

    private fun setSuccess(card: CardDomain) {
        _cardDetailsState.update { currentState -> currentState.copy(card = card.toPres()) }
    }

    private fun setError(error: String?) {
        _cardDetailsState.update { currentState -> currentState.copy(error = error) }
    }

    private fun setLoading(loading: Boolean) {
        _cardDetailsState.update { currentState -> currentState.copy(loading = loading) }
    }
}
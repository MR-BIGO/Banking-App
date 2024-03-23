package com.example.bankingapp.presentation.screen.new_card

import android.text.Editable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankingapp.data.common.Resource
import com.example.bankingapp.domain.use_case.card_oriented.SaveCardUseCase
import com.example.bankingapp.domain.use_case.formatter.FormatCardCvvUseCase
import com.example.bankingapp.domain.use_case.formatter.FormatCardDateUseCase
import com.example.bankingapp.domain.use_case.formatter.FormatCardNumberUseCase
import com.example.bankingapp.presentation.event.NewCardEvents
import com.example.bankingapp.presentation.mapper.toDomain
import com.example.bankingapp.presentation.model.CardPres
import com.example.bankingapp.presentation.state.NewCardState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewCardViewModel @Inject constructor(
    private val cardNumberFormatter: FormatCardNumberUseCase,
    private val cardDateFormatter: FormatCardDateUseCase,
    private val cardCvvFormatter: FormatCardCvvUseCase,
    private val addCard: SaveCardUseCase
) : ViewModel() {

    private val _newCardState = MutableStateFlow(NewCardState())
    val newCardState = _newCardState.asStateFlow()

    fun onEvent(event: NewCardEvents) {
        when (event) {
            is NewCardEvents.CardNumberChanged -> {
                formatCardNumber(event.number)
            }

            is NewCardEvents.CardDateChanged -> {
                formatCardDate(event.date)
            }

            is NewCardEvents.CardCvvChanged -> {
                formatCardCvv(event.cvv)
            }

            is NewCardEvents.AddCard -> {
                addNewCard(event.cardNum, event.cardDate, event.cardCvv)
            }

            is NewCardEvents.ResetError -> {
                setError(null)
            }
        }
    }

    private fun formatCardNumber(input: Editable) {
        cardNumberFormatter(input)
    }

    private fun formatCardDate(input: Editable) {
        cardDateFormatter(input)
    }

    private fun formatCardCvv(input: Editable) {
        cardCvvFormatter(input)
    }

    private fun addNewCard(cardNum: String, cardDate: String, cardCvv: String) {
        viewModelScope.launch {
            addCard(
                CardPres(
                    cardNum = cardNum,
                    cvv = cardCvv,
                    validDate = cardDate,
                    amountEUR = 0.00,
                    amountGEL = 0.00,
                    amountUSD = 0.00
                ).toDomain()
            ).collect {
                handleResult(it)
            }
        }
    }

    private fun handleResult(result: Resource<String>) {
        when (result) {
            is Resource.Loading -> {
                setLoading(result.loading)
            }

            is Resource.Success -> {
                setSuccess(result.data)
            }

            is Resource.Error -> {
                setError(result.error)
            }
        }
    }

    private fun setSuccess(success: String) {
        _newCardState.update { currentState -> currentState.copy(successMessage = success) }
    }

    private fun setError(error: String?) {
        _newCardState.update { currentState -> currentState.copy(error = error) }
    }

    private fun setLoading(loading: Boolean) {
        _newCardState.update { currentState -> currentState.copy(loading = loading) }
    }
}
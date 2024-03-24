package com.example.bankingapp.presentation.screen.transactions.cards_bottom_sheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankingapp.data.common.Resource
import com.example.bankingapp.domain.use_case.card_oriented.GetCardsUseCase
import com.example.bankingapp.presentation.event.transaction.CardBottomSheetEvents
import com.example.bankingapp.presentation.mapper.toPres
import com.example.bankingapp.presentation.state.transaction.CardsBottomSheetState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardsBottomSheetViewModel @Inject constructor(private val getCardsUseCase: GetCardsUseCase) :
    ViewModel() {

    private val _cardsSheetState = MutableStateFlow(CardsBottomSheetState())
    val cardsSheetState: StateFlow<CardsBottomSheetState> = _cardsSheetState.asStateFlow()

    fun onEvent(event: CardBottomSheetEvents) {
        when (event) {
            is CardBottomSheetEvents.GetCards -> {
                getCards()
            }

            CardBottomSheetEvents.ResetError -> {
                setError(null)
            }
        }
    }

    private fun getCards() {
        viewModelScope.launch {
            getCardsUseCase().collect {
                when (it) {
                    is Resource.Error -> {
                        setError(it.error)
                    }

                    is Resource.Loading -> {
                        setLoading(it.loading)
                    }

                    is Resource.Success -> {
                        _cardsSheetState.update { currentState -> currentState.copy(cards = it.data.map { cardDom -> cardDom.toPres() }) }
                    }
                }
            }
        }
    }

    private fun setError(error: String?) {
        _cardsSheetState.update { currentState -> currentState.copy(error = error) }
    }

    private fun setLoading(loading: Boolean) {
        _cardsSheetState.update { currentState -> currentState.copy(loading = loading) }
    }
}
package com.example.bankingapp.presentation.screen.new_card

import android.text.Editable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankingapp.domain.use_case.card_oriented.SaveCardUseCase
import com.example.bankingapp.domain.use_case.formatter.FormatCardCvvUseCase
import com.example.bankingapp.domain.use_case.formatter.FormatCardDateUseCase
import com.example.bankingapp.domain.use_case.formatter.FormatCardNumberUseCase
import com.example.bankingapp.presentation.event.NewCardEvents
import com.example.bankingapp.presentation.mapper.toDomain
import com.example.bankingapp.presentation.model.CardPres
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class NewCardViewModel @Inject constructor(
    private val cardNumberFormatter: FormatCardNumberUseCase,
    private val cardDateFormatter: FormatCardDateUseCase,
    private val cardCvvFormatter: FormatCardCvvUseCase,
    private val addCard: SaveCardUseCase
) : ViewModel() {

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
            )
        }
    }
}
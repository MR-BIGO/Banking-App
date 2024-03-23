package com.example.bankingapp.presentation.mapper

import com.example.bankingapp.domain.model.CardDomain
import com.example.bankingapp.presentation.model.CardPres

fun CardDomain.toPres(): CardPres{
    return CardPres(
        id = id,
        cardNum = cardNum,
        cvv = cvv,
        validDate = validDate,
        amountGEL = amountGEL,
        amountUSD = amountUSD,
        amountEUR = amountEUR
    )
}
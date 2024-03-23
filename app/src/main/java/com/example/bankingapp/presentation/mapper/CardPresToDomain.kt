package com.example.bankingapp.presentation.mapper

import com.example.bankingapp.domain.model.CardDomain
import com.example.bankingapp.presentation.model.CardPres

fun CardPres.toDomain(): CardDomain {
    return CardDomain(
        id = id,
        cardNum = cardNum,
        cvv = cvv,
        validDate = validDate,
        amountGEL = amountGEL,
        amountUSD = amountUSD,
        amountEUR = amountEUR
    )
}
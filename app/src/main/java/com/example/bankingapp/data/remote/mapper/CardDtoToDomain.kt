package com.example.bankingapp.data.remote.mapper

import com.example.bankingapp.data.remote.model.CardDto
import com.example.bankingapp.domain.model.CardDomain

fun CardDto.toDomain(): CardDomain {
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
package com.example.bankingapp.presentation.mapper

import com.example.bankingapp.domain.model.TransactionDomain
import com.example.bankingapp.presentation.model.TransactionPres

fun TransactionDomain.toPres(): TransactionPres {
    return TransactionPres(
        id = id,
        merchant = merchant,
        amount = amount,
        currency = currency,
    )
}
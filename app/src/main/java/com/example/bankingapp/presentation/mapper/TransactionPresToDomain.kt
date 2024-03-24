package com.example.bankingapp.presentation.mapper

import com.example.bankingapp.domain.model.TransactionDomain
import com.example.bankingapp.presentation.model.TransactionPres

fun TransactionPres.toDomain(): TransactionDomain {
    return TransactionDomain(
        id = id,
        merchant = merchant,
        amount = amount,
        currency = currency
    )
}
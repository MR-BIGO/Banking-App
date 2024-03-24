package com.example.bankingapp.data.remote.mapper

import com.example.bankingapp.data.remote.model.TransactionDto
import com.example.bankingapp.domain.model.TransactionDomain

fun TransactionDto.toDomain(): TransactionDomain{
    return TransactionDomain(
        id = id,
        merchant = merchant,
        amount = amount,
        currency = currency,
    )
}
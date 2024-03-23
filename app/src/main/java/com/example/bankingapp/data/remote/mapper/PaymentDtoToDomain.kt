package com.example.bankingapp.data.remote.mapper

import com.example.bankingapp.data.remote.model.PaymentDto
import com.example.bankingapp.data.remote.model.StoryDto
import com.example.bankingapp.domain.model.PaymentDomain
import com.example.bankingapp.domain.model.StoryDomain


fun PaymentDto.toDomain(): PaymentDomain {
    return PaymentDomain(
        id = id,
        name = name,
        type = EnumMapper.mapPaymentDtoCategoryToDomain(type),
    )
}
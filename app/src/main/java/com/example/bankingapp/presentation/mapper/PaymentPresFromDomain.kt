package com.example.bankingapp.presentation.mapper

import com.example.bankingapp.data.remote.mapper.EnumMapper
import com.example.bankingapp.domain.model.PaymentDomain
import com.example.bankingapp.presentation.model.PaymentPres


fun PaymentDomain.toPresentation(): PaymentPres {
    return PaymentPres(
        id = id,
        name = name,
        type = EnumMapper.mapPaymentDomainCategoryToPres(type),
    )
}
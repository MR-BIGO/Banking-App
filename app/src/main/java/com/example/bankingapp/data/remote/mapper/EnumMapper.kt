package com.example.bankingapp.data.remote.mapper

import com.example.bankingapp.data.remote.model.PaymentDto
import com.example.bankingapp.domain.model.PaymentDomain
import com.example.bankingapp.presentation.model.PaymentPres


object EnumMapper {
    fun mapPaymentDtoCategoryToDomain(dataEnum: PaymentDto.CategoryType): PaymentDomain.CategoryType {
        return when (dataEnum) {
            PaymentDto.CategoryType.University -> PaymentDomain.CategoryType.University
            PaymentDto.CategoryType.Delivery -> PaymentDomain.CategoryType.Delivery
            PaymentDto.CategoryType.Utilities -> PaymentDomain.CategoryType.Utilities
            PaymentDto.CategoryType.bet -> PaymentDomain.CategoryType.bet
        }
    }

    fun mapPaymentDomainCategoryToPres(dataEnum: PaymentDomain.CategoryType): PaymentPres.CategoryType {
        return when (dataEnum){
            PaymentDomain.CategoryType.University -> PaymentPres.CategoryType.University
            PaymentDomain.CategoryType.Delivery -> PaymentPres.CategoryType.Delivery
            PaymentDomain.CategoryType.Utilities -> PaymentPres.CategoryType.Utilities
            PaymentDomain.CategoryType.bet -> PaymentPres.CategoryType.bet
        }
    }
}
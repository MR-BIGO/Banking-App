package com.example.bankingapp.presentation.event.transaction

sealed class PayMerchantEvents {
    data class PayPressed(val name: String, val amount: Double, val currency: String) :
        PayMerchantEvents()

    data object ResetError: PayMerchantEvents()
    data object ResetSuccess: PayMerchantEvents()
}

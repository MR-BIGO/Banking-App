package com.example.bankingapp.presentation.event.transaction

sealed class CardBottomSheetEvents {
    data object GetCards : CardBottomSheetEvents()
    data object ResetError : CardBottomSheetEvents()
}

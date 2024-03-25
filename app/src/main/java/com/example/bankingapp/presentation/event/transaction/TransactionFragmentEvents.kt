package com.example.bankingapp.presentation.event.transaction

sealed class TransactionFragmentEvents{
    data object ResetError: TransactionFragmentEvents()
    data object GetTransactions: TransactionFragmentEvents()

}

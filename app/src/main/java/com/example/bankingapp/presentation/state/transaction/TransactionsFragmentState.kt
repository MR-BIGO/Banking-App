package com.example.bankingapp.presentation.state.transaction

import com.example.bankingapp.presentation.model.TransactionPres

data class TransactionsFragmentState (
    val error: String? = null,
    val loading: Boolean = false,
    val transactions: List<TransactionPres>? = null
)
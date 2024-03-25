package com.example.bankingapp.presentation.screen.transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankingapp.data.common.Resource
import com.example.bankingapp.domain.use_case.transaction.GetTransactionsUseCase
import com.example.bankingapp.presentation.event.transaction.TransactionFragmentEvents
import com.example.bankingapp.presentation.mapper.toPres
import com.example.bankingapp.presentation.state.authentication.AuthenticationState
import com.example.bankingapp.presentation.state.transaction.TransactionsFragmentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionsFragmentViewModel @Inject constructor(
    private val getTransactionsUseCase: GetTransactionsUseCase
) : ViewModel() {

    private val _transactionState = MutableStateFlow(TransactionsFragmentState())
    val transactionState: SharedFlow<TransactionsFragmentState> = _transactionState.asStateFlow()

    fun onEvent(event: TransactionFragmentEvents) {
        when (event) {
            TransactionFragmentEvents.GetTransactions -> {getTransactions()}
            TransactionFragmentEvents.ResetError -> {
                setError(null)
            }
        }
    }

    private fun getTransactions() {
        viewModelScope.launch {
            getTransactionsUseCase().collect {
                when (it) {
                    is Resource.Error -> setError(it.error)
                    is Resource.Loading -> setLoading(it.loading)
                    is Resource.Success -> {
                        _transactionState.update { currentState -> currentState.copy(transactions = it.data.map { transaction -> transaction.toPres() }) }
                    }
                }
            }
        }
    }

    private fun setError(error: String?) {
        _transactionState.update { currentState -> currentState.copy(error = error) }
    }

    private fun setLoading(loading: Boolean) {
        _transactionState.update { currentState -> currentState.copy(loading = loading) }
    }
}
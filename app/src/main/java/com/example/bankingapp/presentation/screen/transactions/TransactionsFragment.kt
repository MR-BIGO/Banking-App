package com.example.bankingapp.presentation.screen.transactions

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bankingapp.databinding.FragmentTransactionsBinding
import com.example.bankingapp.presentation.base.BaseFragment
import com.example.bankingapp.presentation.event.transaction.TransactionFragmentEvents
import com.example.bankingapp.presentation.screen.home.adapter.TransactionsRecyclerAdapter
import com.example.bankingapp.presentation.state.transaction.TransactionsFragmentState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TransactionsFragment :
    BaseFragment<FragmentTransactionsBinding>(FragmentTransactionsBinding::inflate) {
    private val viewModel: TransactionsFragmentViewModel by viewModels()
    private val transactionsRvAdapter = TransactionsRecyclerAdapter()

    override fun setUp() {
        viewModel.onEvent(TransactionFragmentEvents.GetTransactions)
        setUpTransactionsRv()
    }

    override fun listeners() = with(binding) {
        ivPayment.setOnClickListener {
            findNavController().navigate(TransactionsFragmentDirections.actionTransactionsFragmentToPaymentFragment())
        }
        ivTransferToOwn.setOnClickListener {
            findNavController().navigate(TransactionsFragmentDirections.actionTransactionsFragmentToTransferToOwnAccFragment())
        }
        ivTransferToSomeoneElse.setOnClickListener {
            findNavController().navigate(TransactionsFragmentDirections.actionTransactionsFragmentToTransferToSomeoneElseFragment())
        }
    }

    private fun handleState(state: TransactionsFragmentState) {
        state.error?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.onEvent(TransactionFragmentEvents.ResetError)
        }
        binding.progressBar.visibility = if (state.loading) View.VISIBLE else View.GONE

        state.transactions?.let {
            transactionsRvAdapter.setData(it)
        }
    }

    private fun setUpTransactionsRv()= with(binding){
        rvTransactions.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = transactionsRvAdapter
        }
    }

    override fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.transactionState.collect {
                    handleState(it)
                }
            }
        }
    }

}
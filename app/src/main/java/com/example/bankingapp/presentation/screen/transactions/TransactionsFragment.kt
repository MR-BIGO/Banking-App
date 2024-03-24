package com.example.bankingapp.presentation.screen.transactions

import androidx.navigation.fragment.findNavController
import com.example.bankingapp.databinding.FragmentTransactionsBinding
import com.example.bankingapp.presentation.base.BaseFragment


class TransactionsFragment : BaseFragment<FragmentTransactionsBinding>(FragmentTransactionsBinding::inflate) {

    override fun setUp() {
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
        ivExchange.setOnClickListener {
            findNavController().navigate(TransactionsFragmentDirections.actionTransactionsFragmentToExchangeFragment())
        }
    }
    override fun observers() {
    }

}
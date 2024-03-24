package com.example.bankingapp.presentation.screen.transactions.exchange

import androidx.navigation.fragment.findNavController
import com.example.bankingapp.databinding.FragmentExchangeBinding
import com.example.bankingapp.presentation.base.BaseFragment


class ExchangeFragment : BaseFragment<FragmentExchangeBinding>(FragmentExchangeBinding::inflate) {


    override fun setUp() {

    }

    override fun listeners() {
        goBack()
    }

    private fun goBack() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}
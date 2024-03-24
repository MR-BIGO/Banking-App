package com.example.bankingapp.presentation.screen.transactions.payment.pay_to_merchant

import androidx.fragment.app.viewModels
import com.example.bankingapp.databinding.FragmentPayToMerchantBinding
import com.example.bankingapp.presentation.base.BaseFragment


class PayToMerchantFragment :
    BaseFragment<FragmentPayToMerchantBinding>(FragmentPayToMerchantBinding::inflate) {

    private val viewModel: PayToMerchantViewModel by viewModels()


    override fun setUp() {


    }

    override fun listeners() {

    }

    override fun observers() {

    }

}
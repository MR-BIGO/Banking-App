package com.example.bankingapp.presentation.screen.transactions.to_someone_else

import androidx.navigation.fragment.findNavController
import com.example.bankingapp.databinding.FragmentTransferToSomeoneElseBinding
import com.example.bankingapp.presentation.base.BaseFragment

class TransferToSomeoneElseFragment :
    BaseFragment<FragmentTransferToSomeoneElseBinding>(FragmentTransferToSomeoneElseBinding::inflate) {

    override fun setUp() {

    }

    override fun listeners() {
        goBack()
    }


    private fun goBack(){
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}
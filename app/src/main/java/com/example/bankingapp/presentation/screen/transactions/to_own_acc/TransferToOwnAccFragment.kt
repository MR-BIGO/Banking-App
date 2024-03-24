package com.example.bankingapp.presentation.screen.transactions.to_own_acc

import androidx.navigation.fragment.findNavController
import com.example.bankingapp.databinding.FragmentTransferToOwnAccBinding
import com.example.bankingapp.presentation.base.BaseFragment


class TransferToOwnAccFragment : BaseFragment<FragmentTransferToOwnAccBinding>(FragmentTransferToOwnAccBinding::inflate) {


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
package com.example.bankingapp.presentation.screen.transactions

import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.navigation.fragment.findNavController
import com.example.bankingapp.databinding.FragmentTransactionsBinding
import com.example.bankingapp.presentation.base.BaseFragment


class TransactionsFragment : BaseFragment<FragmentTransactionsBinding>(FragmentTransactionsBinding::inflate) {

    override fun setUp() {
        setUpBtnAnim()
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

    private fun setUpBtnAnim() {
        val anim = AlphaAnimation(0.8f, 1.0f)
        anim.apply {
            duration = 1200
            repeatMode = Animation.REVERSE
            repeatCount = Animation.INFINITE
        }
        with(binding){
            ivPayment.startAnimation(anim)
            ivTransferToOwn.startAnimation(anim)
            ivTransferToSomeoneElse.startAnimation(anim)
        }

    }
}
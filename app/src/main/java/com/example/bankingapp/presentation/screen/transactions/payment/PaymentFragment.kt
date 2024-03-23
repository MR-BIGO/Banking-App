package com.example.bankingapp.presentation.screen.transactions.payment

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bankingapp.databinding.FragmentPaymentBinding
import com.example.bankingapp.presentation.base.BaseFragment
import com.example.bankingapp.presentation.event.HomeFragmentEvents
import com.example.bankingapp.presentation.event.PaymentFragmentEvents
import com.example.bankingapp.presentation.screen.home.HomeViewModel
import com.example.bankingapp.presentation.screen.home.adapter.StoriesRecyclerViewAdapter
import com.example.bankingapp.presentation.screen.transactions.payment.adapter.PaymentRecyclerAdapter
import com.example.bankingapp.presentation.state.HomeState
import com.example.bankingapp.presentation.state.PaymentState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PaymentFragment : BaseFragment<FragmentPaymentBinding>(FragmentPaymentBinding::inflate) {


    private val paymentRecyclerAdapter = PaymentRecyclerAdapter()
    private val viewModel: PaymentFragmentViewModel by viewModels()

    override fun setUp() {
        setupPaymentRV()
        viewModel.onEvent(PaymentFragmentEvents.GetPayments)

    }

    override fun listeners() {
        paymentRecyclerAdapter.itemOnClick = {

        }
    }

    override fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.paymentState.collect {
                    handleState(it)
                }
            }
        }
    }

    private fun handleState(state: PaymentState) {
        state.payments?.let {
            paymentRecyclerAdapter.setData(it)
        }
        state.error?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            Log.d("check error cards here", it)
        }
    }

    private fun setupPaymentRV(){
        binding.rvPayments.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = paymentRecyclerAdapter
        }
    }
}
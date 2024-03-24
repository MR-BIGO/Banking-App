package com.example.bankingapp.presentation.screen.transactions.payment

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bankingapp.databinding.FragmentPaymentBinding
import com.example.bankingapp.presentation.base.BaseFragment
import com.example.bankingapp.presentation.event.PaymentFragmentEvents
import com.example.bankingapp.presentation.screen.transactions.payment.adapter.PaymentRecyclerAdapter
import com.example.bankingapp.presentation.state.transaction.PaymentState
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

    override fun listeners() = with(binding) {
        paymentRecyclerAdapter.itemOnClick = {
            viewModel.onEvent(PaymentFragmentEvents.PaymentPressed(it))
        }

        btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.paymentState.collect {
                        handleState(it)
                    }
                }
                launch {
                    viewModel.uiEvent.collect {
                        handleEvent(it)
                    }
                }

            }
        }
    }

    private fun handleEvent(event: PaymentFragmentViewModel.PaymentsNavigationEvents) {
        when (event) {
            is PaymentFragmentViewModel.PaymentsNavigationEvents.NavigateToMerchantPayment -> {
                navigateToMerchantPayment(event.name)
            }
        }
    }

    private fun navigateToMerchantPayment(name: String) {
        findNavController().navigate(
            PaymentFragmentDirections.actionPaymentFragmentToPayToMerchantFragment(
                name
            )
        )
    }

    private fun handleState(state: PaymentState) {
        state.payments?.let {
            paymentRecyclerAdapter.setData(it)
        }
        state.error?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
        binding.progressBar.visibility = if (state.loading) View.VISIBLE else View.GONE
    }

    private fun setupPaymentRV() {
        binding.rvPayments.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = paymentRecyclerAdapter
        }
    }
}
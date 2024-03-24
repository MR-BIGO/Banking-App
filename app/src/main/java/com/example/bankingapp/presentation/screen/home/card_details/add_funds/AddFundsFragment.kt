package com.example.bankingapp.presentation.screen.home.card_details.add_funds

import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.bankingapp.R
import com.example.bankingapp.databinding.FragmentAddFundsBinding
import com.example.bankingapp.presentation.base.BaseFragment
import com.example.bankingapp.presentation.event.card.add_funds.AddFundsEvents
import com.example.bankingapp.presentation.state.home.AddFundsState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddFundsFragment : BaseFragment<FragmentAddFundsBinding>(FragmentAddFundsBinding::inflate) {

    private val viewModel: AddFundsFragmentViewModel by viewModels()
    private val args: AddFundsFragmentArgs by navArgs()

    private var gelDouble = 0.0
    private var usdDouble = 0.0
    private var euroDouble = 0.0

    override fun setUp() {
        viewModel.onEvent(AddFundsEvents.GetCardInfo(args.id))
    }

    override fun listeners() = with(binding) {
        btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        btnFillFunds.setOnClickListener {
            if (validator()) {
                moneyMapper()
            }
        }
    }

    private fun handleState(state: AddFundsState) = with(binding) {
        state.card?.let {
            tvMaskedNumber.text = "**** **** **** ".plus(it.cardNum.takeLast(4))
            tvAmountGel.text =
                it.amountGEL.toString().plus(" ").plus(resources.getString(R.string.symbol_gel))
            tvAmountEur.text =
                it.amountEUR.toString().plus(" ").plus(resources.getString(R.string.symbol_eur))
            tvAmountUsd.text =
                it.amountUSD.toString().plus(" ").plus(resources.getString(R.string.symbol_usd))

            gelDouble = it.amountGEL
            usdDouble = it.amountUSD
            euroDouble = it.amountEUR

            when (it.cardNum.first()) {
                '4' -> {
                    ivPaymentCorp.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            root.resources,
                            R.drawable.ic_visa,
                            root.resources.newTheme()
                        )
                    )
                }

                '5' -> {
                    ivPaymentCorp.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            root.resources,
                            R.drawable.ic_mastercard,
                            root.resources.newTheme()
                        )
                    )
                }

                else -> {
                    ivPaymentCorp.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            root.resources,
                            R.drawable.ic_paypal,
                            root.resources.newTheme()
                        )
                    )
                }
            }
        }

        if (state.updateSuccess) {
            viewModel.onEvent(AddFundsEvents.GetCardInfo(args.id))
            viewModel.onEvent(AddFundsEvents.ResetSuccess)
        }

        if (state.loading){
            progressBar.visibility = View.VISIBLE
            disable()
        }else{
            progressBar.visibility = View.GONE
            enable()
        }
    }

    private fun disable() = with(binding) {
        fundsGelEditText.isEnabled = false
        fundsUsdEditText.isEnabled = false
        fundsEuroEditText.isEnabled = false
    }

    private fun enable() = with(binding) {
        fundsGelEditText.isEnabled = true
        fundsUsdEditText.isEnabled = true
        fundsEuroEditText.isEnabled = true
    }

    private fun validator(): Boolean = with(binding) {
        if (fundsGelEditText.text.toString().isEmpty() && fundsUsdEditText.text.toString()
                .isEmpty() && fundsEuroEditText.text.toString().isEmpty()
        ) {
            setEmptyErrors()
            return false
        }
        return true
    }

    private fun moneyMapper() = with(binding) {
        val gel = fundsGelEditText.text.toString()
        val usd = fundsUsdEditText.text.toString()
        val euro = fundsEuroEditText.text.toString()

        if (gel.isNotEmpty()) gelDouble += gel.toDouble()
        if (usd.isNotEmpty()) usdDouble += usd.toDouble()
        if (euro.isNotEmpty()) euroDouble += euro.toDouble()

        viewModel.onEvent(AddFundsEvents.FillBalancePressed(gelDouble, usdDouble, euroDouble))
    }

    private fun setEmptyErrors() = with(binding) {
        fundsGelEditText.error = "Please, enter funds"
        fundsUsdEditText.error = "Please, enter funds"
        fundsEuroEditText.error = "Please, enter funds"
    }

    override fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.addFundsState.collect {
                    handleState(it)
                }
            }
        }
    }
}
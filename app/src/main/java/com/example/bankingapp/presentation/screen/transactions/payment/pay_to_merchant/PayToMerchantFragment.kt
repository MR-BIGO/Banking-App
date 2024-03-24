package com.example.bankingapp.presentation.screen.transactions.payment.pay_to_merchant

import android.view.View
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.bankingapp.R
import com.example.bankingapp.databinding.FragmentPayToMerchantBinding
import com.example.bankingapp.presentation.base.BaseFragment
import com.example.bankingapp.presentation.event.transaction.PayMerchantEvents
import com.example.bankingapp.presentation.model.CardPres
import com.example.bankingapp.presentation.notification.NotificationService
import com.example.bankingapp.presentation.screen.transactions.cards_bottom_sheet.CardsBottomSheet
import com.example.bankingapp.presentation.state.transaction.PayToMerchantState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PayToMerchantFragment :
    BaseFragment<FragmentPayToMerchantBinding>(FragmentPayToMerchantBinding::inflate) {

    private val viewModel: PayToMerchantViewModel by viewModels()
    private val args: PayToMerchantFragmentArgs by navArgs()
    private var cardGel = 0.0
    private var chosenCard: CardPres? = null
    private lateinit var service: NotificationService

    override fun setUp() {
        binding.tvMerchantName.text = args.name
        service = NotificationService(requireContext())
    }

    override fun listeners() = with(binding) {
        btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        btnMakePayment.setOnClickListener {
            if (emptyCheck()) {
                if (moneyCheck(cardGel, fundsGelEditText.text.toString().toDouble())) {
                    viewModel.onEvent(
                        PayMerchantEvents.PayPressed(
                            args.name,
                            fundsGelEditText.text.toString().toDouble(),
                            "Gel",
                            chosenCard!!
                        )
                    )
                } else {
                    fundsGelEditText.error = "Not enough funds"
                }
            } else {
                fundsGelEditText.error = "Enter payment amount"
            }
        }

        cardView.setOnClickListener {
            val bottomSheet = CardsBottomSheet().apply {
                setListener(object : CardsBottomSheet.BottomSheetListener {
                    override fun onOptionSelected(option: CardPres) {
                        setChosenCard(option)
                    }
                })
            }
            bottomSheet.show(childFragmentManager, bottomSheet.tag)
        }
    }

    private fun setChosenCard(card: CardPres) = with(binding) {
        tvCardGelAmount.text =
            card.amountGEL.toString().plus(" ").plus(root.resources.getString(R.string.symbol_gel))

        tvLastFour.text = "**** ".plus(card.cardNum.takeLast(4))
        cardGel = card.amountGEL
        chosenCard = card

        viewModel.onEvent(PayMerchantEvents.ChosenCard(card))

        when (card.cardNum.first()) {
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

    private fun moneyCheck(amountOnCard: Double, toPay: Double): Boolean {
        if (amountOnCard < toPay) return false
        return true
    }

    private fun emptyCheck(): Boolean {
        if (binding.fundsGelEditText.text.toString().isEmpty()) return false
        return true
    }

    private fun handleState(state: PayToMerchantState) = with(binding) {
        if (state.loading) {
            progressBar.visibility = View.VISIBLE
            disable()
        } else {
            progressBar.visibility = View.GONE
            enable()
        }

        if (state.successTransaction && state.successCard) {
            service.showNotification(args.name)
            findNavController().popBackStack()
        }

        state.error?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.onEvent(PayMerchantEvents.ResetError)
        }
    }

    private fun disable() {
        binding.fundsGelEditText.isEnabled = false
        binding.btnMakePayment.isClickable = false
    }

    private fun enable() {
        binding.fundsGelEditText.isEnabled = true
        binding.btnMakePayment.isClickable = true
    }

    override fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.payMerchantState.collect {
                    handleState(it)
                }
            }
        }
    }

}
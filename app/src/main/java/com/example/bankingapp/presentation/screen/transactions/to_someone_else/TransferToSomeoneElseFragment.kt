package com.example.bankingapp.presentation.screen.transactions.to_someone_else

import android.view.View
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.bankingapp.R
import com.example.bankingapp.databinding.FragmentTransferToSomeoneElseBinding
import com.example.bankingapp.presentation.base.BaseFragment
import com.example.bankingapp.presentation.event.transaction.TransferOwnEvents
import com.example.bankingapp.presentation.event.transaction.TransferToElseEvents
import com.example.bankingapp.presentation.model.CardPres
import com.example.bankingapp.presentation.notification.NotificationService
import com.example.bankingapp.presentation.screen.transactions.cards_bottom_sheet.CardsBottomSheet
import com.example.bankingapp.presentation.state.transaction.TransferToElseState
import com.example.bankingapp.presentation.state.transaction.TransferToOwnState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TransferToSomeoneElseFragment :
    BaseFragment<FragmentTransferToSomeoneElseBinding>(FragmentTransferToSomeoneElseBinding::inflate) {

    private val viewModel: TransferToElseViewModel by viewModels()
    private lateinit var service: NotificationService
    private var cardGel = 0.0
    private var chosenCardFrom: CardPres? = null

    override fun setUp() {
        service = NotificationService(requireContext())

    }

    override fun listeners(): Unit = with(binding) {
        btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        cardView.setOnClickListener {
            val bottomSheet = CardsBottomSheet().apply {
                setListener(object : CardsBottomSheet.BottomSheetListener {
                    override fun onOptionSelected(option: CardPres) {
                        setFromChosenCard(option)
                    }
                })
            }
            bottomSheet.show(childFragmentManager, bottomSheet.tag)
        }
        idNumberEditText.text?.let {
            idNumberEditText.doOnTextChanged { _, _, _, _ ->
                if (validateIdNum(it.toString())) {
                    cardView2.visibility = View.VISIBLE
                } else {
                    cardView2.visibility = View.INVISIBLE
                }
            }
        }

        btnTransfer.setOnClickListener {
            if (emptyCheck()) {
                if (moneyCheck(cardGel, fundsGelEditText.text.toString().toDouble())) {
                    viewModel.onEvent(
                        TransferToElseEvents.TransferPressed(
                            fundsGelEditText.text.toString().toDouble(), "GEL", chosenCardFrom!!
                        )
                    )
                } else {
                    fundsGelEditText.error = "Not enough funds"
                }
            } else {
                fundsGelEditText.error = "Enter transfer amount"
            }
        }

    }

    private fun validateIdNum(text: String): Boolean {
        if (text.length != 11) return false
        return true
    }

    private fun emptyCheck(): Boolean {
        if (binding.fundsGelEditText.text.toString().isEmpty()) return false
        return true
    }

    private fun moneyCheck(amountOnCard: Double, toPay: Double): Boolean {
        if (amountOnCard < toPay) return false
        return true
    }

    private fun disable() {
        binding.fundsGelEditText.isEnabled = false
        binding.btnTransfer.isClickable = false
    }

    private fun enable() {
        binding.fundsGelEditText.isEnabled = true
        binding.btnTransfer.isClickable = true
    }

    private fun setFromChosenCard(card: CardPres) = with(binding) {
        tvCardGelAmountFrom.text =
            card.amountGEL.toString().plus(" ").plus(root.resources.getString(R.string.symbol_gel))

        tvLastFourFrom.text = "**** ".plus(card.cardNum.takeLast(4))
        cardGel = card.amountGEL
        chosenCardFrom = card

        viewModel.onEvent(TransferToElseEvents.ChosenCardFrom(card))

        when (card.cardNum.first()) {
            '4' -> {
                ivPaymentCorpFrom.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        root.resources,
                        R.drawable.ic_visa,
                        root.resources.newTheme()
                    )
                )
            }

            '5' -> {
                ivPaymentCorpFrom.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        root.resources,
                        R.drawable.ic_mastercard,
                        root.resources.newTheme()
                    )
                )
            }

            else -> {
                ivPaymentCorpFrom.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        root.resources,
                        R.drawable.ic_paypal,
                        root.resources.newTheme()
                    )
                )
            }
        }
    }

    private fun handleState(state: TransferToElseState) = with(binding) {
        if (state.loading) {
            progressBar.visibility = View.VISIBLE
            disable()
        } else {
            progressBar.visibility = View.GONE
            enable()
        }

        if (state.successTransaction && state.successCardFrom) {
            service.showNotification()
            findNavController().popBackStack()
        }

        state.error?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.onEvent(TransferToElseEvents.ResetError)
        }

        if (state.chosenCardFrom == null) {
            disable()
        } else {
            enable()
        }
    }

    override fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.transferElseState.collect {
                    handleState(it)
                }
            }
        }
    }

}
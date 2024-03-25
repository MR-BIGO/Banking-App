package com.example.bankingapp.presentation.screen.home.card_details

import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.bankingapp.R
import com.example.bankingapp.databinding.FragmentCardDetailsBinding
import com.example.bankingapp.presentation.base.BaseFragment
import com.example.bankingapp.presentation.event.card.details_card.CardDetailsEvents
import com.example.bankingapp.presentation.state.home.CardDetailsState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CardDetailsFragment :
    BaseFragment<FragmentCardDetailsBinding>(FragmentCardDetailsBinding::inflate) {
    private val viewModel: CardDetailsViewModel by viewModels()
    private val args: CardDetailsFragmentArgs by navArgs()
    private var cardNum = ""
    private var cvv = ""
    private var validDate = ""

    override fun setUp() {
        viewModel.onEvent(CardDetailsEvents.GetCardDetails(args.id))
    }

    override fun listeners() = with(binding) {
        btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        btnShowDetails.setOnClickListener {
            viewModel.onEvent(CardDetailsEvents.ShowDetailsPressed(cardNum, validDate, cvv))
        }

        ivOwn.setOnClickListener {
            findNavController().navigate(CardDetailsFragmentDirections.actionCardDetailsFragmentToTransferToOwnAccFragment())
        }
        btnElse.setOnClickListener{
            findNavController().navigate(CardDetailsFragmentDirections.actionCardDetailsFragmentToTransferToSomeoneElseFragment())
        }

        btnDeleteCard.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Are you sure you want to delete this card?")
            builder.setPositiveButton("Yes") { dialog, _ ->
                viewModel.onEvent(CardDetailsEvents.DeleteCardPressed(args.id))
                dialog.dismiss()
            }
            builder.setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            builder.show()

        }

        btnAddFunds.setOnClickListener {
            viewModel.onEvent(CardDetailsEvents.AddFundsPressed(args.id))
        }

        btnTransferOwn.setOnClickListener {
            viewModel.onEvent(CardDetailsEvents.TransferToOwnPressed)
        }

        btnTransferElse.setOnClickListener {
            viewModel.onEvent(CardDetailsEvents.TransferToElsePressed)
        }
    }


    private fun handleState(state: CardDetailsState) = with(binding) {
        state.card?.let {
            tvAmountGel.text =
                it.amountGEL.toString().plus(" ").plus(resources.getString(R.string.symbol_gel))
            tvAmountEur.text =
                it.amountEUR.toString().plus(" ").plus(resources.getString(R.string.symbol_eur))
            tvAmountUsd.text =
                it.amountUSD.toString().plus(" ").plus(resources.getString(R.string.symbol_usd))

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
            cardNum = it.cardNum
            cvv = it.cvv
            validDate = it.validDate
        }
        if (state.deleteSuccess) {
            findNavController().popBackStack()
        }

        progressBar.visibility = if (state.loading) View.VISIBLE else View.GONE
    }

    private fun handleEvent(event: CardDetailsViewModel.DetailsNavigationEvent) {
        when (event) {
            is CardDetailsViewModel.DetailsNavigationEvent.NavigateToDetails -> {
                openDetailsBottomSheet(event.cardNum, event.validDate, event.cvv)
            }

            is CardDetailsViewModel.DetailsNavigationEvent.NavigateToTransferElse -> {}
            is CardDetailsViewModel.DetailsNavigationEvent.NavigateToTransferOwn -> {}
            is CardDetailsViewModel.DetailsNavigationEvent.NavigateToFunds -> {
                navigateToFunds(event.id)
            }
        }
    }

    private fun navigateToFunds(id: String) {
        findNavController().navigate(
            CardDetailsFragmentDirections.actionCardDetailsFragmentToAddFundsFragment(
                id
            )
        )
    }

    private fun openDetailsBottomSheet(cardNum: String, validDate: String, cvv: String) {
        findNavController().navigate(
            CardDetailsFragmentDirections.actionCardDetailsFragmentToCardDetailsDialogFragment(
                cardNum,
                validDate,
                cvv
            )
        )
    }

    override fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.cardDetailsState.collect {
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
}
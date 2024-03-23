package com.example.bankingapp.presentation.screen.home.card_details

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.bankingapp.databinding.FragmentCardDetailsBinding
import com.example.bankingapp.presentation.base.BaseFragment
import com.example.bankingapp.presentation.event.card.details_card.CardDetailsEvents
import com.example.bankingapp.presentation.state.CardDetailsState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CardDetailsFragment :
    BaseFragment<FragmentCardDetailsBinding>(FragmentCardDetailsBinding::inflate) {
    private val viewModel: CardDetailsViewModel by viewModels()
    private val args: CardDetailsFragmentArgs by navArgs()

    override fun setUp() {
        viewModel.onEvent(CardDetailsEvents.GetCardDetails(args.id))
    }

    override fun listeners() = with(binding){
        btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun handleState(state: CardDetailsState) {

    }

    override fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.cardDetailsState.collect {
                    handleState(it)
                }
            }
        }
    }
}
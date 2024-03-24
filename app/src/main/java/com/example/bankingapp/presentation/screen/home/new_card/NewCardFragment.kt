package com.example.bankingapp.presentation.screen.home.new_card

import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.bankingapp.databinding.FragmentNewCardBinding
import com.example.bankingapp.presentation.base.BaseFragment
import com.example.bankingapp.presentation.event.card.new_card.NewCardEvents
import com.example.bankingapp.presentation.state.home.NewCardState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewCardFragment : BaseFragment<FragmentNewCardBinding>(FragmentNewCardBinding::inflate) {
    private val viewModel: NewCardViewModel by viewModels()

    override fun listeners(): Unit = with(binding) {
        btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        cardNumberEditText.text.let {
            cardNumberEditText.doOnTextChanged { _, _, _, _ ->
                viewModel.onEvent(NewCardEvents.CardNumberChanged(it!!))
            }
        }

        cardDateEditText.text.let {
            cardDateEditText.doOnTextChanged { _, _, _, _ ->
                viewModel.onEvent(NewCardEvents.CardDateChanged(it!!))
            }
        }

        cardCvvEditText.text.let {
            cardCvvEditText.doOnTextChanged { _, _, _, _ ->
                viewModel.onEvent(NewCardEvents.CardCvvChanged(it!!))
            }
        }

        btnAddCard.setOnClickListener {
            viewModel.onEvent(
                NewCardEvents.AddCard(
                    cardNumberEditText.text.toString(),
                    cardDateEditText.text.toString(),
                    cardCvvEditText.text.toString()
                )
            )
        }
    }

    private fun handleState(state: NewCardState)= with(binding){
        state.error?.let {
        }
        state.successMessage?.let {
            returnToHome()
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
        cardNumberEditText.isEnabled = false
        cardDateEditText.isEnabled = false
        cardCvvEditText.isEnabled = false
    }

    private fun enable() = with(binding) {
        cardNumberEditText.isEnabled = true
        cardDateEditText.isEnabled = true
        cardCvvEditText.isEnabled = true
    }

    private fun returnToHome(){
        findNavController().popBackStack()
    }

    override fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.newCardState.collect{
                    handleState(it)
                }
            }
        }
    }
}
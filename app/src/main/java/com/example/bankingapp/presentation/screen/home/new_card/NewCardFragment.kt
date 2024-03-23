package com.example.bankingapp.presentation.screen.home.new_card

import android.util.Log.d
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
import com.example.bankingapp.presentation.state.NewCardState
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
            d("checking database result", it)
        }
        state.successMessage?.let {
            d("checking database result", it)
            returnToHome()
        }

        progressBar.visibility = if (state.loading) View.VISIBLE else View.GONE
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
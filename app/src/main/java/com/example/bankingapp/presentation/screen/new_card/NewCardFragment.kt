package com.example.bankingapp.presentation.screen.new_card

import android.util.Log.d
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bankingapp.databinding.FragmentNewCardBinding
import com.example.bankingapp.presentation.base.BaseFragment
import com.example.bankingapp.presentation.event.NewCardEvents
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewCardFragment : BaseFragment<FragmentNewCardBinding>(FragmentNewCardBinding::inflate) {
    private val viewModel: NewCardViewModel by viewModels()
    override fun setUp() {

    }

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

}
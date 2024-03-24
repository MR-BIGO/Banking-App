package com.example.bankingapp.presentation.screen.transactions.cards_bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bankingapp.databinding.CardsBottomSheetBinding
import com.example.bankingapp.presentation.event.transaction.CardBottomSheetEvents
import com.example.bankingapp.presentation.model.CardPres
import com.example.bankingapp.presentation.screen.transactions.cards_bottom_sheet.adapter.CardsRecyclerAdapter
import com.example.bankingapp.presentation.state.transaction.CardsBottomSheetState
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CardsBottomSheet : BottomSheetDialogFragment() {

    private var _binding: CardsBottomSheetBinding? = null
    private val binding get() = _binding!!
    fun setListener(listener: BottomSheetListener) {
        this.listener = listener
    }

    interface BottomSheetListener {
        fun onOptionSelected(option: CardPres)
    }

    private var listener: BottomSheetListener? = null

    private val viewModel: CardsBottomSheetViewModel by viewModels()
    private val cardsRecyclerAdapter = CardsRecyclerAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CardsBottomSheetBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        setUpRv()
        bindObservers()
        listeners()
        viewModel.onEvent(CardBottomSheetEvents.GetCards)
    }

    private fun setUpRv() {
        binding.rvCards.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = cardsRecyclerAdapter
        }
    }

    private fun listeners(){
        cardsRecyclerAdapter.itemOnClick = {
            listener?.onOptionSelected(it)
            dismiss()
        }
    }

    private fun handleState(state: CardsBottomSheetState) {
        state.cards?.let {
            cardsRecyclerAdapter.setData(it)
        }

        binding.progressBar.visibility = if (state.loading) View.VISIBLE else View.GONE
        state.error?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.onEvent(CardBottomSheetEvents.ResetError)
        }
    }

    private fun bindObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.cardsSheetState.collect {
                    handleState(it)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
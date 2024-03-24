package com.example.bankingapp.presentation.screen.home.card_details.card_full_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.bankingapp.databinding.FragmentCardDetailsBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardDetailsDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentCardDetailsBottomSheetBinding? = null
    private val binding get() = _binding!!
    private val args: CardDetailsDialogFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCardDetailsBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() = with(binding) {
        tvCardNum.text = args.cardNum
        tvValidDate.text = args.validDate
        tvCvv.text = args.cvv
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
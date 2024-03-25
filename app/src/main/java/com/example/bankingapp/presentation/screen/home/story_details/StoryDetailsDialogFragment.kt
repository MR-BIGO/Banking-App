package com.example.bankingapp.presentation.screen.home.story_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.bankingapp.databinding.FragmentStoryDetailsBottomSheetBinding
import com.example.bankingapp.presentation.extensions.loadImage
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class StoryDetailsDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentStoryDetailsBottomSheetBinding? = null
    private val binding get() = _binding!!
    private val args: StoryDetailsDialogFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStoryDetailsBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() = with(binding) {
        imageView.loadImage(args.StoryDetails.picture)
        tvTitle.text = args.StoryDetails.title
        tvText.text = args.StoryDetails.text
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
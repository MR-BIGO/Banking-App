package com.example.bankingapp.presentation.screen.auth.register

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bankingapp.databinding.FragmentRegisterBinding
import com.example.bankingapp.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {
    private val viewModel: RegisterFragmentViewModel by viewModels()

    override fun setUp() {

    }

    override fun listeners() = with(binding) {
        btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun observers() {

    }

}
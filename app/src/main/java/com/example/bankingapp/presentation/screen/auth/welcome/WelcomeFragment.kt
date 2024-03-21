package com.example.bankingapp.presentation.screen.auth.welcome

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.bankingapp.databinding.FragmentWelcomeBinding
import com.example.bankingapp.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>(FragmentWelcomeBinding::inflate) {
    private val viewModel: WelcomeViewModel by viewModels()
    override fun setUp() {

    }

    override fun listeners() {
        binding.apply {
            btnRegister.setOnClickListener {
                findNavController().navigate(WelcomeFragmentDirections.actionWelcomeFragmentToRegisterFragment())
            }
            btnLogin.setOnClickListener {
                findNavController().navigate(WelcomeFragmentDirections.actionWelcomeFragmentToLogInFragment())
            }
        }
    }

    override fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isUserLoggedIn.collect {
                    if (it)
                        findNavController().navigate(
                            WelcomeFragmentDirections.actionWelcomeFragmentToMainFragment()
                        )
                }
            }
        }
    }
}
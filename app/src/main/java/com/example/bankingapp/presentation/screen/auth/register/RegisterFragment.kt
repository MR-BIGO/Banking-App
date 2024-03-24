package com.example.bankingapp.presentation.screen.auth.register

import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.bankingapp.databinding.FragmentRegisterBinding
import com.example.bankingapp.presentation.base.BaseFragment
import com.example.bankingapp.presentation.event.authentication.RegisterEvents
import com.example.bankingapp.presentation.state.authentication.AuthenticationState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {
    private val viewModel: RegisterFragmentViewModel by viewModels()

    override fun listeners() = with(binding) {
        signUpButton.setOnClickListener {
            viewModel.onEvent(
                RegisterEvents.RegisterPressed(
                    emailEditText.text.toString(),
                    passwordEditText.text.toString(),
                    confirmPasswordEditText.text.toString()
                )
            )
        }

        tvAlreadyAccount.setOnClickListener {
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLogInFragment())
        }
    }

    private fun navigateToLogin(email: String?, password: String?) {
        setFragmentResult(
            "RegisterResult",
            bundleOf(
                "email" to email,
                "password" to password
            )
        )
        findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLogInFragment())
    }

    private fun handleState(state: AuthenticationState) = with(binding) {
        if (state.isLoading) {
            progressBar.visibility = View.VISIBLE
            disable()
        } else {
            progressBar.visibility = View.GONE
            enable()
        }
    }

    private fun handleEvent(event: RegisterFragmentViewModel.RegisterNavigationEvents) {
        when (event) {
            is RegisterFragmentViewModel.RegisterNavigationEvents.NavigateToLogin -> {
                navigateToLogin(event.email, event.password)
            }
        }
    }

    private fun disable() = with(binding) {
        emailEditText.isEnabled = false
        passwordEditText.isEnabled = false
        confirmPasswordEditText.isEnabled = false
    }

    private fun enable() = with(binding) {
        emailEditText.isEnabled = true
        passwordEditText.isEnabled = true
        confirmPasswordEditText.isEnabled = true
    }

    override fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.registerState.collect {
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
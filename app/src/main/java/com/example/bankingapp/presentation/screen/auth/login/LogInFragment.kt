package com.example.bankingapp.presentation.screen.auth.login

import android.view.View
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.bankingapp.databinding.FragmentLogInBinding
import com.example.bankingapp.presentation.base.BaseFragment
import com.example.bankingapp.presentation.event.authentication.LoginEvents
import com.example.bankingapp.presentation.state.authentication.AuthenticationState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LogInFragment : BaseFragment<FragmentLogInBinding>(FragmentLogInBinding::inflate) {
    private val viewModel: LogInFragmentViewModel by viewModels()

    override fun setUp() {
        resultListener()
    }

    override fun listeners() = with(binding) {
        tvNoAccount.setOnClickListener {
            findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToRegisterFragment())
        }

        signInButton.setOnClickListener {
            viewModel.onEvent(
                LoginEvents.LoginPressed(
                    emailEditText.text.toString(),
                    passwordEditText.text.toString()
                )
            )
        }
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

    private fun handleEvent(event: LogInFragmentViewModel.LoginNavigationEvents) {
        when (event) {
            is LogInFragmentViewModel.LoginNavigationEvents.NavigateToHome -> {
                navigateToHome()
            }
        }
    }

    private fun navigateToHome() {
        findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToMainFragment())
    }

    private fun disable() = with(binding) {
        emailEditText.isClickable = false
        passwordEditText.isClickable = false
    }

    private fun enable() = with(binding) {
        emailEditText.isClickable = true
        passwordEditText.isClickable = true
    }

    private fun resultListener() = with(binding) {
        setFragmentResultListener("RegisterResult") { _, bundle ->
            val email = bundle.getString("email")
            val password = bundle.getString("password")
            emailEditText.setText(email)
            passwordEditText.setText(password)
        }
    }

    override fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.loginState.collect {
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
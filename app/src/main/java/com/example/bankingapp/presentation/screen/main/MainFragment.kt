package com.example.bankingapp.presentation.screen.main

import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.bankingapp.R
import com.example.bankingapp.databinding.FragmentMainBinding
import com.example.bankingapp.presentation.base.BaseFragment


class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    override fun setUp() {
        val bottomNavigationView = binding.bottomNavigationView
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.fragmentContainerView2) as NavHostFragment
        val navController = navHostFragment.navController
        bottomNavigationView.setupWithNavController(navController)

        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.newCardFragment -> {
                    binding.bottomNavigationView.visibility = View.GONE
                }
                R.id.paymentFragment -> {
                    binding.bottomNavigationView.visibility = View.GONE
                }
                R.id.transferToOwnAccFragment -> {
                    binding.bottomNavigationView.visibility = View.GONE
                }
                R.id.transferToSomeoneElseFragment -> {
                    binding.bottomNavigationView.visibility = View.GONE
                }
                R.id.exchangeFragment -> {
                    binding.bottomNavigationView.visibility = View.GONE
                }
                R.id.cardDetailsFragment -> {
                    binding.bottomNavigationView.visibility = View.GONE
                }
                R.id.addFundsFragment -> {
                    binding.bottomNavigationView.visibility = View.GONE
                }
                R.id.cardDetailsDialogFragment -> {
                    binding.bottomNavigationView.visibility = View.GONE
                }
                else -> {
                    binding.bottomNavigationView.visibility = View.VISIBLE
                }
            }
        }
    }


}
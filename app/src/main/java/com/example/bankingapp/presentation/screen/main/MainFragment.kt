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
            if (destination.id == R.id.newCardFragment) {
                binding.bottomNavigationView.visibility = View.GONE
            } else if (destination.id == R.id.paymentFragment) {
                binding.bottomNavigationView.visibility = View.GONE
            } else if (destination.id == R.id.transferToOwnAccFragment) {
                binding.bottomNavigationView.visibility = View.GONE
            } else if (destination.id == R.id.transferToSomeoneElseFragment) {
                binding.bottomNavigationView.visibility = View.GONE
            } else if (destination.id == R.id.exchangeFragment) {
                binding.bottomNavigationView.visibility = View.GONE
            } else {
                binding.bottomNavigationView.visibility = View.VISIBLE
            }
        }
    }


}
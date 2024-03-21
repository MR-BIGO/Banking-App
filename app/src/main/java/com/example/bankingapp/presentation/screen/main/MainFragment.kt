package com.example.bankingapp.presentation.screen.main

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

    }

    override fun observers() {

    }

}
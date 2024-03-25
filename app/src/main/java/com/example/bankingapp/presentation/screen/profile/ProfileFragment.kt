package com.example.bankingapp.presentation.screen.profile

import android.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.bankingapp.R
import com.example.bankingapp.databinding.FragmentProfileBinding
import com.example.bankingapp.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val viewModel: ProfileFragmentViewModel by viewModels()


    override fun listeners() {
        binding.btnLogOut.setOnClickListener {


            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Are you sure you want to delete this card?")
            builder.setPositiveButton("Yes") { dialog, _ ->
                viewModel.logOut()
                navigateToLogin()
                dialog.dismiss()
            }
            builder.setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            builder.show()
        }
    }
    override fun observers() {

    }

    private fun navigateToLogin(){
        Navigation.findNavController(binding.root).navigate(R.id.nav_host_fragment)
    }

}
package com.example.bankingapp.presentation.screen.home

import android.util.Log.d
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bankingapp.databinding.FragmentHomeBinding
import com.example.bankingapp.presentation.base.BaseFragment
import com.example.bankingapp.presentation.event.HomeFragmentEvents
import com.example.bankingapp.presentation.screen.home.adapter.CardsRecyclerViewAdapter
import com.example.bankingapp.presentation.screen.home.adapter.StoriesRecyclerViewAdapter
import com.example.bankingapp.presentation.state.HomeState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val storiesRecyclerAdapter = StoriesRecyclerViewAdapter()
    private val cardsRecyclerAdapter = CardsRecyclerViewAdapter()
    private val viewModel: HomeViewModel by viewModels()
    override fun setUp() {
        with(binding.recyclerViewStories) {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = storiesRecyclerAdapter
        }
        viewModel.onEvent(HomeFragmentEvents.GetStories)
        viewModel.onEvent(HomeFragmentEvents.GetCards)
        setUpCards()
    }

    override fun listeners() = with(binding) {
        btnAddCard.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToNewCardFragment())
        }
    }

    override fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.homeState.collect {
                    handleState(it)
                }
            }
        }
    }

    private fun setUpCards(){
        binding.recyclerCards.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = cardsRecyclerAdapter
        }
    }

    private fun handleState(state: HomeState) {
        with(state) {
            stories?.let {
                storiesRecyclerAdapter.submitList(it)
            }
            cards?.let {
                cardsRecyclerAdapter.setData(it)
            }
            state.error?.let {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                d("check error cards here", it)
                viewModel.onEvent(HomeFragmentEvents.ResetError)
            }

            binding.progressBar.visibility = if (state.loading) View.VISIBLE else View.GONE
        }
    }
}
package com.example.bankingapp.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankingapp.data.common.Resource
import com.example.bankingapp.domain.use_case.GetStoriesUseCase
import com.example.bankingapp.presentation.event.HomeFragmentEvents
import com.example.bankingapp.presentation.mapper.toPresentation
import com.example.bankingapp.presentation.state.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getStoriesUseCase: GetStoriesUseCase,
    ): ViewModel() {

    private val _homeState = MutableStateFlow(HomeState())
    val homeState = _homeState.asStateFlow()

    fun onEvent(event: HomeFragmentEvents) {
        when (event) {
            is HomeFragmentEvents.ResetError -> setError(null)

            is HomeFragmentEvents.GetStories -> {
                getStories()
            }

        }
    }

    private fun setError(error: String?) {
        viewModelScope.launch {
            _homeState.update { currentState -> currentState.copy(error = error) }
        }
    }


    private fun getStories() {
        viewModelScope.launch {
            getStoriesUseCase().collect {
                when (it) {
                    is Resource.Success -> {
                        _homeState.update { currentState -> currentState.copy(stories = it.data.map { story -> story.toPresentation() }) }
                    }

                    is Resource.Error -> {
                        setError(it.error)
                    }
                    is Resource.Loading -> {
                        _homeState.update { currentState -> currentState.copy(loading = it.loading) }
                    }

                    else -> {}
                }
            }
        }
    }
}
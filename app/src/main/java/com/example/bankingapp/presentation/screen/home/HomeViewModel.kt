package com.example.bankingapp.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankingapp.data.common.Resource
import com.example.bankingapp.domain.use_case.GetStoriesUseCase
import com.example.bankingapp.domain.use_case.card_oriented.GetCardsUseCase
import com.example.bankingapp.presentation.event.HomeFragmentEvents
import com.example.bankingapp.presentation.mapper.toPres
import com.example.bankingapp.presentation.mapper.toPresentation
import com.example.bankingapp.presentation.state.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getStoriesUseCase: GetStoriesUseCase,
    private val getCardsUseCase: GetCardsUseCase
) : ViewModel() {

    private val _homeState = MutableStateFlow(HomeState())
    val homeState: SharedFlow<HomeState> = _homeState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<HomeNavigationEvents>()
    val uiEvent: SharedFlow<HomeNavigationEvents> get() = _uiEvent

    fun onEvent(event: HomeFragmentEvents) {
        when (event) {
            is HomeFragmentEvents.ResetError -> setError(null)

            is HomeFragmentEvents.GetStories -> {
                getStories()
            }

            is HomeFragmentEvents.GetCards -> {
                getCards()
            }

            is HomeFragmentEvents.CardPressed -> {
                handleCardPress(event.id)
            }

        }
    }

    private fun setError(error: String?) {
        _homeState.update { currentState -> currentState.copy(error = error) }
    }

    private fun setLoading(loading: Boolean) {
        _homeState.update { currentState -> currentState.copy(loading = loading) }
    }

    private fun getCards() {
        viewModelScope.launch {
            getCardsUseCase().collect {
                when (it) {
                    is Resource.Success -> {
                        _homeState.update { currentState -> currentState.copy(cards = it.data.map { card -> card.toPres() }) }
                    }

                    is Resource.Error -> {
                        setError(error = it.error)
                    }

                    is Resource.Loading -> {
                        setLoading(it.loading)
                    }
                }
            }
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
                        setLoading(it.loading)
                    }
                }
            }
        }
    }

    private fun handleCardPress(id: String){
        viewModelScope.launch {
            _uiEvent.emit(HomeNavigationEvents.NavigateToDetails(id))
        }
    }

    sealed class HomeNavigationEvents{
        data class NavigateToDetails(val id: String): HomeNavigationEvents()
    }
}
package com.example.bankingapp.presentation.event

sealed class HomeFragmentEvents {
    data object ResetError : HomeFragmentEvents()
    data object GetStories : HomeFragmentEvents()
    data object GetCards: HomeFragmentEvents()
}

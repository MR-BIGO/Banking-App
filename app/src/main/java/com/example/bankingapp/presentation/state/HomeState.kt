package com.example.bankingapp.presentation.state

import com.example.bankingapp.presentation.model.StoryPres

data class HomeState (
    val loading: Boolean = false,
    val stories: List<StoryPres>? = null,
    val error: String? = null
)
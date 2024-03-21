package com.example.bankingapp.presentation.state

import com.example.bankingapp.presentation.model.StoryUiModel

data class HomeState (
    val loading: Boolean = false,
    val stories: List<StoryUiModel>? = null,
    val error: String? = null
)
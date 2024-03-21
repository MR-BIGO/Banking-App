package com.example.bankingapp.presentation.mapper

import com.example.bankingapp.domain.model.StoryDomainModel
import com.example.bankingapp.presentation.model.StoryUiModel

fun StoryDomainModel.toPresentation(): StoryUiModel {
    return StoryUiModel(
        id = id,
        picture = picture,
        text = text,
        title = title,
    )
}

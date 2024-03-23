package com.example.bankingapp.presentation.mapper

import com.example.bankingapp.domain.model.StoryDomain
import com.example.bankingapp.presentation.model.StoryPres

fun StoryDomain.toPresentation(): StoryPres {
    return StoryPres(
        id = id,
        picture = picture,
        text = text,
        title = title,
        color = color
    )
}

package com.example.bankingapp.data.remote.mapper

import com.example.bankingapp.data.remote.model.StoryDto
import com.example.bankingapp.domain.model.StoryDomain


fun StoryDto.toDomain(): StoryDomain {
    return StoryDomain(
        id = id,
        picture = picture,
        text = text,
        title = title,
        color = color
    )
}
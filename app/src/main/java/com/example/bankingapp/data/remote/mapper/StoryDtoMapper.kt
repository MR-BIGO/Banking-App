package com.example.bankingapp.data.remote.mapper

import com.example.bankingapp.data.remote.model.StoryDto
import com.example.bankingapp.domain.model.StoryDomainModel


fun StoryDto.toDomain(): StoryDomainModel {
    return StoryDomainModel(
        id = id,
        picture = picture,
        text = text,
        title = title,
    )
}
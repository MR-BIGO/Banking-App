package com.example.bankingapp.domain.repository

import com.example.bankingapp.data.common.Resource
import com.example.bankingapp.domain.model.StoryDomain
import kotlinx.coroutines.flow.Flow

interface StoriesRepository {
    suspend fun getStories(): Flow<Resource<List<StoryDomain>>>
}
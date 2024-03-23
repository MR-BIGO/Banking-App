package com.example.bankingapp.domain.use_case

import com.example.bankingapp.data.common.Resource
import com.example.bankingapp.domain.model.StoryDomain
import com.example.bankingapp.domain.repository.StoriesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStoriesUseCase @Inject constructor(private val repository: StoriesRepository) {

    suspend operator fun invoke(): Flow<Resource<List<StoryDomain>>> {
        return repository.getStories()
    }
}
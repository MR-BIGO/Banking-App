package com.example.bankingapp.data.repository.remote

import com.example.bankingapp.data.common.Resource
import com.example.bankingapp.data.remote.mapper.base.mapListToDomain
import com.example.bankingapp.data.remote.mapper.toDomain
import com.example.bankingapp.data.remote.service.StoriesApiService
import com.example.bankingapp.data.remote.util.HandleResponse
import com.example.bankingapp.domain.model.StoryDomainModel
import com.example.bankingapp.domain.repository.StoriesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StoriesRepositoryImpl @Inject constructor(
    private val service: StoriesApiService,
    private val handler: HandleResponse
) : StoriesRepository {
    override suspend fun getStories(): Flow<Resource<List<StoryDomainModel>>> {
        return handler.safeApiCall { service.getStories() }.mapListToDomain { it.toDomain() }
    }
}
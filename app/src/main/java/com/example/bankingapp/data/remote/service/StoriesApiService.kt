package com.example.bankingapp.data.remote.service

import com.example.bankingapp.data.remote.model.StoryDto
import retrofit2.Response
import retrofit2.http.GET

interface StoriesApiService {
    @GET("e032b8b5-da80-4e4c-8dd2-a1cf8b8ffb2a")
    suspend fun getStories(): Response<List<StoryDto>>
}

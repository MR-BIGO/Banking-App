package com.example.bankingapp.data.remote.service

import com.example.bankingapp.data.remote.model.StoryDto
import retrofit2.Response
import retrofit2.http.GET

interface StoriesApiService {
    @GET("4463a264-ed92-48a4-8ac1-57cf79ad1807")
    suspend fun getStories(): Response<List<StoryDto>>
}

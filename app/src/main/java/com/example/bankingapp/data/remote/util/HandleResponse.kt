package com.example.bankingapp.data.remote.util

import com.example.bankingapp.data.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class HandleResponse {
    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>): Flow<Resource<T>> = flow {
        emit(Resource.Loading(true))
        try {
            val response = call()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                emit(Resource.Success(body))
            } else {
                emit(Resource.Error(response.errorBody()?.toString() ?: ""))
            }
        } catch (e: Throwable) {
            emit(Resource.Error(e.message ?: ""))
        }
        emit(Resource.Loading(false))
    }
}
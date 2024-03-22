package com.example.bankingapp.data.remote.mapper.base

import com.example.bankingapp.data.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun <T, DomainType> Flow<Resource<List<T>>>.mapListToDomain(mapper: (T) -> DomainType): Flow<Resource<List<DomainType>>> {
    return this.map { resource ->
        when (resource) {
            is Resource.Success -> Resource.Success(resource.data.map(mapper))
            is Resource.Loading -> Resource.Loading(resource.loading)
            is Resource.Error -> Resource.Error(resource.error)
        }
    }
}
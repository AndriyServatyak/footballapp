package com.incrowdsports.task.data

import com.incrowdsports.task.data.models.BaseApiResponse
import com.incrowdsports.task.data.models.FixtureResponse
import com.incrowdsports.task.data.models.MatchResponse
import com.incrowdsports.task.data.models.NetworkResult
import com.incrowdsports.task.data.remote.RemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class Repository(
    private val remoteDataSource: RemoteDataSource
) : BaseApiResponse() {

    suspend fun getFixtureList(
        compId: Int,
        season: Int,
        size: Int,
    ): Flow<NetworkResult<FixtureResponse>> {
        return flow {
            emit(NetworkResult.Loading())
            emit(safeApiCall { remoteDataSource.getFixtureList(compId, season, size) })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getMatchDetails(matchId: Long): Flow<NetworkResult<MatchResponse>> {
        return flow {
            emit(NetworkResult.Loading())
            emit(safeApiCall { remoteDataSource.getMatchDetails(matchId) })
        }.flowOn(Dispatchers.IO)
    }

}


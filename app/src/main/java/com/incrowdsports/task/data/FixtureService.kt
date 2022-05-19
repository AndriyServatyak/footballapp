package com.incrowdsports.task.data

import com.incrowdsports.task.data.models.FixtureResponse
import com.incrowdsports.task.data.models.MatchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FixtureService {

    @GET("matches")
    suspend fun getFixtureList(
        @Query("compId") compId: Int,
        @Query("season") season: Int,
        @Query("size") size: Int,
    ): Response<FixtureResponse>

    @GET("matches/{id}")
    suspend fun getMatchDetails(@Path("id") id: Long): Response<MatchResponse>

}
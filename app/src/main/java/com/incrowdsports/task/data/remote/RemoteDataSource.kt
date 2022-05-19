package com.incrowdsports.task.data.remote

import com.incrowdsports.task.data.FixtureService

class RemoteDataSource(private val fixtureService: FixtureService) {

    suspend fun getFixtureList(
        compId: Int,
        season: Int,
        size: Int,
    ) = fixtureService.getFixtureList(compId, season, size)

    suspend fun getMatchDetails(matchId: Long) = fixtureService.getMatchDetails(matchId)

}
package com.incrowdsports.task.data.models

data class MatchResponse(
    var data: MatchData
)

data class MatchData(
    val feedMatchId: Int,
    val competition: String,
    val season: String,
    val date: String,
    var homeTeam: Team,
    var awayTeam: Team
)

package com.incrowdsports.task.data.models

data class FixtureResponse(
    val data: List<Fixture>
)

data class Fixture(
    val id: String,
    val feedMatchId: Long,
    val competition: String,
    val period: String,
    val date: String,
    val homeTeam: Team,
    val awayTeam: Team,
    val venue: Venue,
)

data class Team(
    val id: String,
    val name: String,
    val score: Int,
    val players: List<Player>,
    val imageUrl: String
)

data class Player(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val position: String,
    val shirtNumber: Int
)

data class Venue(
    val id: String,
    val name: String,
)
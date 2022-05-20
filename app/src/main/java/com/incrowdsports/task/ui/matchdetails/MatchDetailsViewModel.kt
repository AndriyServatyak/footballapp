package com.incrowdsports.task.ui.matchdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.incrowdsports.task.data.Repository
import com.incrowdsports.task.data.models.MatchResponse
import com.incrowdsports.task.data.models.NetworkResult
import com.incrowdsports.task.data.models.Player
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MatchDetailsViewModel(
    private val repository: Repository
) : ViewModel() {

    private var matchId: Long? = null

    private val _tvHomeTeamNameLiveData = MutableLiveData<String>()
    val tvHomeTeamNameLiveData: LiveData<String> = _tvHomeTeamNameLiveData

    private val _tvAwayTeamNameLiveData = MutableLiveData<String>()
    val tvAwayTeamNameLiveData: LiveData<String> = _tvAwayTeamNameLiveData

    private val _tvHomeTeamScoreLiveData = MutableLiveData<String>()
    val tvHomeTeamScoreLiveData: LiveData<String> = _tvHomeTeamScoreLiveData

    private val _tvAwayTeamScoreLiveData = MutableLiveData<String>()
    val tvAwayTeamScoreLiveData: LiveData<String> = _tvAwayTeamScoreLiveData

    private val _homePlayersLiveData = MutableLiveData<List<Player>>()
    val homePlayersLiveData: LiveData<List<Player>> = _homePlayersLiveData

    private val _awayPlayersLiveData = MutableLiveData<List<Player>>()
    val awayPlayersLiveData: LiveData<List<Player>> = _awayPlayersLiveData

    private val _tvCompetitionLiveData = MutableLiveData<String>()
    val tvCompetitionLiveData: LiveData<String> = _tvCompetitionLiveData

    private val _tvSeasonLiveData = MutableLiveData<String>()
    val tvSeasonLiveData: LiveData<String> = _tvSeasonLiveData

    private val _ivHomeTeamLogoLiveData = MutableLiveData<String>()
    val ivHomeTeamLogoLiveData: LiveData<String> = _ivHomeTeamLogoLiveData

    private val _ivAwayTeamLogoLiveData = MutableLiveData<String>()
    val ivAwayTeamLogoLiveData: LiveData<String> = _ivAwayTeamLogoLiveData

    private val _progressVisibilityLiveData = MutableLiveData<Boolean>()
    val progressVisibilityLiveData: LiveData<Boolean> = _progressVisibilityLiveData

    fun onViewCreated(matchId: Long?) {
        this.matchId = matchId
        loadData()
    }

    private fun loadData() {
        matchId?.let {
            viewModelScope.launch(Dispatchers.Main) {
                repository.getMatchDetails(it).collect {
                    when (it) {
                        is NetworkResult.Error -> _progressVisibilityLiveData.value = false
                        is NetworkResult.Loading -> _progressVisibilityLiveData.value = true
                        is NetworkResult.Success -> {
                            _progressVisibilityLiveData.value = false
                            updateUI(it.data)
                        }
                    }
                }
            }
        }
    }

    private fun updateUI(data: MatchResponse?) {
        data?.let {
            _tvHomeTeamNameLiveData.value = it.data.homeTeam.name
            _tvAwayTeamNameLiveData.value = it.data.awayTeam.name

            _tvHomeTeamScoreLiveData.value = it.data.homeTeam.score.toString()
            _tvAwayTeamScoreLiveData.value = it.data.awayTeam.score.toString()

            _homePlayersLiveData.value = it.data.homeTeam.players
            _awayPlayersLiveData.value = it.data.awayTeam.players

            _tvCompetitionLiveData.value = it.data.competition
            _tvSeasonLiveData.value = it.data.season

            _ivHomeTeamLogoLiveData.value = it.data.homeTeam.imageUrl
            _ivAwayTeamLogoLiveData.value = it.data.awayTeam.imageUrl
        }
    }

}
package com.incrowdsports.task.ui.fixture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.incrowdsports.task.data.Repository
import com.incrowdsports.task.data.models.FixtureResponse
import com.incrowdsports.task.data.models.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FixtureListViewModel(private val repository: Repository) : ViewModel() {

    private var compId: Int = 0
    private var season: Int = 0

    private val _response: MutableLiveData<NetworkResult<FixtureResponse>> = MutableLiveData()
    val response: LiveData<NetworkResult<FixtureResponse>> = _response

    fun onResume(compId: Int, season: Int) {
        this.compId = compId
        this.season = season
        loadData()
    }

    fun onRefreshCalled() {
        loadData()
    }


    private fun loadData() {
        viewModelScope.launch(Dispatchers.Main) {
            repository.getFixtureList(compId = compId, season = season, size = 10).collect {
                _response.value = it
            }
        }
    }
}
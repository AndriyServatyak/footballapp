package com.incrowdsports.task.ui.fixture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.incrowdsports.task.data.Repository
import com.incrowdsports.task.data.models.Fixture
import com.incrowdsports.task.data.models.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FixtureListViewModel(private val repository: Repository) : ViewModel() {

    private val _progressVisibilityLiveData = MutableLiveData<Boolean>()
    val progressVisibilityLiveData: LiveData<Boolean> = _progressVisibilityLiveData

    private val _rvMatchesListLiveData = MutableLiveData<MutableList<Fixture>>()
    val rvMatchesListLiveData: LiveData<MutableList<Fixture>> = _rvMatchesListLiveData

    fun onResume() {
        loadData()
    }

    fun onRefreshCalled() {
        loadData()
    }


    private fun loadData() {
        viewModelScope.launch(Dispatchers.Main) {
            repository.getFixtureList(compId = COMP_ID, season = SEASON, size = PAGE_SIZE).collect {
                when (it) {
                    is NetworkResult.Error -> _progressVisibilityLiveData.value = false
                    is NetworkResult.Loading -> _progressVisibilityLiveData.value = true
                    is NetworkResult.Success -> {
                        _progressVisibilityLiveData.value = false
                        _rvMatchesListLiveData.value = it.data?.data?.toMutableList()
                    }
                }
            }
        }
    }

    companion object {
        private const val PAGE_SIZE = 10
        private const val COMP_ID = 8
        private const val SEASON = 2021
    }
}
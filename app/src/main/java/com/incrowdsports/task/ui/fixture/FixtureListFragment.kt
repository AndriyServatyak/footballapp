package com.incrowdsports.task.ui.fixture

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.incrowdsports.task.R
import com.incrowdsports.task.data.models.Fixture
import com.incrowdsports.task.data.models.NetworkResult
import com.incrowdsports.task.databinding.FixtureListFragmentBinding
import com.incrowdsports.task.ui.MainActivity
import com.incrowdsports.task.ui.adapter.FixtureListAdapter
import com.incrowdsports.task.ui.matchdetails.MatchDetailsFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class FixtureListFragment : Fragment(R.layout.fixture_list_fragment) {

    private val binding by lazy { FixtureListFragmentBinding.bind(requireView()) }
    private val viewModel: FixtureListViewModel by viewModel()
    private val fixtureListAdapter = FixtureListAdapter {
        (activity as MainActivity).supportFragmentManager.commit {
            addToBackStack(null)
            replace(R.id.fragmentContainerView, MatchDetailsFragment.newInstance(it.feedMatchId))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observeViewModel()
    }

    private fun setupUI() {
        binding.swipeRefreshLayout.setOnRefreshListener { viewModel.onRefreshCalled() }
        binding.recyclerView.apply {
            adapter = fixtureListAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume(COMP_ID, SEASON)
    }

    private fun observeViewModel() {
        viewModel.response.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Error -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                }
                is NetworkResult.Loading -> {
                    binding.swipeRefreshLayout.isRefreshing = true
                }
                is NetworkResult.Success -> renderFixtureList(
                    fixtureList = response.data?.data ?: arrayListOf()
                )
            }
        }
    }

    private fun renderFixtureList(fixtureList: List<Fixture>) {
        binding.swipeRefreshLayout.isRefreshing = false
        fixtureListAdapter.submitList(fixtureList)
    }

    companion object {
        fun newInstance() = FixtureListFragment()
        private const val COMP_ID = 8
        private const val SEASON = 2021

    }
}
package com.incrowdsports.task.ui.fixture

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.incrowdsports.task.R
import com.incrowdsports.task.data.models.Fixture
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
        viewModel.onResume()
    }

    private fun observeViewModel() {
        viewModel.progressVisibilityLiveData.observe(viewLifecycleOwner) {
            binding.swipeRefreshLayout.isRefreshing = it
        }

        viewModel.rvMatchesListLiveData.observe(viewLifecycleOwner) {
            renderFixtureList(it)
        }
    }

    private fun renderFixtureList(fixtureList: List<Fixture>) {
        binding.swipeRefreshLayout.isRefreshing = false
        fixtureListAdapter.submitList(fixtureList)
    }

    companion object {
        fun newInstance() = FixtureListFragment()
    }
}
package com.incrowdsports.task.ui.matchdetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import coil.load
import com.incrowdsports.task.R
import com.incrowdsports.task.data.models.NetworkResult
import com.incrowdsports.task.databinding.FragmentMatchDetailsBinding
import com.incrowdsports.task.ui.adapter.PlayersListAdapter
import com.incrowdsports.task.ui.fixture.FixtureListFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MatchDetailsFragment : Fragment(R.layout.fragment_match_details) {

    private val binding by lazy { FragmentMatchDetailsBinding.bind(requireView()) }
    private val viewModel: MatchDetailsViewModel by viewModel()
    private val homePlayersAdapter = PlayersListAdapter()
    private val awayPlayersAdapter = PlayersListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observeViewModel()
        viewModel.onViewCreated(arguments?.getLong(MATCH_ID_BUNDLE_KEY))
    }

    private fun setupUI() {
        binding.rvHomePlayers.adapter = homePlayersAdapter
        binding.rvAwayPlayers.adapter = awayPlayersAdapter
    }

    private fun observeViewModel() {
        viewModel.response.observe(viewLifecycleOwner) {
            when(it) {
                is NetworkResult.Loading -> binding.swipeRefreshLayout.isRefreshing = true
                is NetworkResult.Error,
                is NetworkResult.Success -> binding.swipeRefreshLayout.isRefreshing = false
            }
        }

        viewModel.tvHomeTeamNameLiveData.observe(viewLifecycleOwner) {
            binding.tvTeamHomeName.text = it
        }

        viewModel.tvAwayTeamNameLiveData.observe(viewLifecycleOwner) {
            binding.tvTeamAwayName.text = it
        }

        viewModel.tvHomeTeamScoreLiveData.observe(viewLifecycleOwner) {
            binding.tvTeamHomeScore.text = it
        }

        viewModel.tvAwayTeamScoreLiveData.observe(viewLifecycleOwner) {
            binding.tvTeamAwayScore.text = it
        }

        viewModel.homePlayersLiveData.observe(viewLifecycleOwner) {
            homePlayersAdapter.submitList(it)
        }

        viewModel.awayPlayersLiveData.observe(viewLifecycleOwner) {
            awayPlayersAdapter.submitList(it)
        }

        viewModel.tvCompetitionLiveData.observe(viewLifecycleOwner) {
            binding.tvCompetition.text = it
        }

        viewModel.tvSeasonLiveData.observe(viewLifecycleOwner) {
            binding.tvSeason.text = it
        }

        viewModel.ivHomeTeamLogoLiveData.observe(viewLifecycleOwner) {
            binding.ivTeamHome.load(it)
        }

        viewModel.ivAwayTeamLogoLiveData.observe(viewLifecycleOwner) {
            binding.ivTeamAway.load(it)
        }
    }

    companion object {

        private const val MATCH_ID_BUNDLE_KEY = "MATCH_ID_BUNDLE_KEY"

        fun newInstance(matchId: Long) = MatchDetailsFragment().apply {
            arguments = Bundle().apply { putLong(MATCH_ID_BUNDLE_KEY, matchId) }
        }
    }
}
package com.incrowdsports.task.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.incrowdsports.task.data.models.Fixture
import com.incrowdsports.task.databinding.FixtureLayoutBinding

class FixtureListAdapter(
    private val onItemClicked: (Fixture) -> Unit
) : ListAdapter<Fixture, RecyclerView.ViewHolder>(
    diffCallback
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FixtureViewHolder(
            FixtureLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onItemClicked
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as FixtureViewHolder).bind(getItem(position))
    }

    private class FixtureViewHolder(
        private val binding: FixtureLayoutBinding,
        val onItemClicked: (Fixture) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var item: Fixture

        init {
            binding.root.setOnClickListener { onItemClicked(item) }
        }

        fun bind(item: Fixture) {
            this.item = item
            binding.competition.text = item.competition
            binding.period.text = item.period
            binding.venue.text = item.venue.name
            binding.homeName.text = item.homeTeam.name
            binding.homeScore.text = item.homeTeam.score.toString()
            binding.awayName.text = item.awayTeam.name
            binding.awayScore.text = item.awayTeam.score.toString()
        }

    }

    private companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Fixture>() {
            override fun areItemsTheSame(oldItem: Fixture, newItem: Fixture): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Fixture, newItem: Fixture): Boolean {
                return oldItem == newItem
            }
        }
    }

}
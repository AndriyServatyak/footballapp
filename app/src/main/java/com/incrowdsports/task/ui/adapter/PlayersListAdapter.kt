package com.incrowdsports.task.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.incrowdsports.task.data.models.Player
import com.incrowdsports.task.databinding.ItemMatchPlayerBinding

class PlayersListAdapter : ListAdapter<Player, RecyclerView.ViewHolder>(
    diffCallback
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FixtureViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as FixtureViewHolder).bind(getItem(position))
    }

    private class FixtureViewHolder(private val binding: ItemMatchPlayerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Player) {
            binding.tvPlayerName.text = "${item.firstName} ${item.lastName}"
            binding.tvPlayerPosition.text = item.position
            binding.tvPlayerShirtName.text = item.shirtNumber.toString()
        }

        companion object {
            fun create(parent: ViewGroup) = FixtureViewHolder(
                ItemMatchPlayerBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

    }

    private companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Player>() {
            override fun areItemsTheSame(oldItem: Player, newItem: Player): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Player, newItem: Player): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        }
    }

}
package com.example.bankingapp.presentation.screen.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bankingapp.databinding.StoryRecyclerItemBinding
import com.example.bankingapp.presentation.extensions.loadImage
import com.example.bankingapp.presentation.model.StoryUiModel


class StoriesRecyclerViewAdapter: ListAdapter<StoryUiModel, StoriesRecyclerViewAdapter.StoriesViewHolder>(StoryDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoriesViewHolder {
        return StoriesViewHolder(StoryRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: StoriesViewHolder, position: Int) {
        holder.bind()
    }

    inner class StoriesViewHolder(private val binding: StoryRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val story = currentList[adapterPosition]
            with(binding) {
                shapeableImageViewCover.loadImage(story.picture)
                tvTitle.text = story.title
            }
        }
    }

    companion object {
        private val StoryDiffCallback = object : DiffUtil.ItemCallback<StoryUiModel>() {

            override fun areItemsTheSame(oldItem: StoryUiModel, newItem: StoryUiModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: StoryUiModel, newItem: StoryUiModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}
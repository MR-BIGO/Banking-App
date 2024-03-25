package com.example.bankingapp.presentation.screen.home.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bankingapp.databinding.StoryRecyclerItemBinding
import com.example.bankingapp.presentation.extensions.loadImage
import com.example.bankingapp.presentation.model.StoryPres


class StoriesRecyclerViewAdapter: ListAdapter<StoryPres, StoriesRecyclerViewAdapter.StoriesViewHolder>(StoryDiffCallback) {

    var onItemClick: (StoryPres) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoriesViewHolder {
        return StoriesViewHolder(StoryRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: StoriesViewHolder, position: Int) {
        holder.bind()
        holder.listener()
    }

    inner class StoriesViewHolder(private val binding: StoryRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val story = currentList[adapterPosition]
            with(binding) {
                shapeableImageViewCover.loadImage(story.picture)
                tvTitle.text = story.title
                tvTitle.setTextColor(Color.parseColor("#".plus(story.color)))
                shapeableImageViewCover.setOnClickListener {
                }
            }
        }

        fun listener() = with(binding) {
            root.setOnClickListener {
                onItemClick.invoke(currentList[adapterPosition])
            }
        }
    }

    companion object {
        private val StoryDiffCallback = object : DiffUtil.ItemCallback<StoryPres>() {

            override fun areItemsTheSame(oldItem: StoryPres, newItem: StoryPres): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: StoryPres, newItem: StoryPres): Boolean {
                return oldItem == newItem
            }
        }
    }
}
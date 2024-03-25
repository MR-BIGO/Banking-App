package com.example.bankingapp.presentation.screen.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bankingapp.R
import com.example.bankingapp.databinding.TransactionRvItemBinding
import com.example.bankingapp.presentation.model.TransactionPres

class TransactionsRecyclerAdapter :
    ListAdapter<TransactionPres, TransactionsRecyclerAdapter.TransactionsViewHolder>(DiffCallback()) {

    inner class TransactionsViewHolder(private val binding: TransactionRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() = with(binding) {
            val transaction = currentList[adapterPosition]

            tvMerchant.text = transaction.merchant
            tvAmount.text = transaction.amount.toString().plus(" ")
                .plus(root.resources.getString(R.string.symbol_gel))
        }
    }

    fun setData(transactions: List<TransactionPres>) {
        submitList(transactions)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionsViewHolder {
        return TransactionsViewHolder(
            TransactionRvItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TransactionsViewHolder, position: Int) {
        holder.bind()
    }

    private class DiffCallback : DiffUtil.ItemCallback<TransactionPres>() {
        override fun areItemsTheSame(
            oldItem: TransactionPres, newItem: TransactionPres
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: TransactionPres, newItem: TransactionPres
        ): Boolean {
            return oldItem == newItem
        }
    }

}
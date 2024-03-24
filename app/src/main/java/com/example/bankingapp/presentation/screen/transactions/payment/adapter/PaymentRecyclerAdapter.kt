package com.example.bankingapp.presentation.screen.transactions.payment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bankingapp.R
import com.example.bankingapp.databinding.PaymentRvItemBinding
import com.example.bankingapp.databinding.StoryRecyclerItemBinding
import com.example.bankingapp.presentation.model.PaymentPres


class PaymentRecyclerAdapter :
    ListAdapter<PaymentPres, PaymentRecyclerAdapter.PaymentsViewHolder>(paymentDiffCallback) {

    var itemOnClick: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentsViewHolder {
        return PaymentsViewHolder(
            PaymentRvItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PaymentsViewHolder, position: Int) {
        holder.bind()
        holder.listener()

    }

    fun setData(payments: List<PaymentPres>) {
        submitList(payments)
    }

    inner class PaymentsViewHolder(private val binding: PaymentRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() = with(binding) {
            val payment = currentList[adapterPosition]

            tvName.text = payment.name
            when (payment.type) {
                PaymentPres.CategoryType.University -> {
                    ivSection.setBackgroundResource(R.drawable.ic_university)
                }

                PaymentPres.CategoryType.Delivery -> {
                    ivSection.setBackgroundResource(R.drawable.ic_delivry)
                }

                PaymentPres.CategoryType.Utilities -> {
                    ivSection.setBackgroundResource(R.drawable.ic_utilities)
                }

                PaymentPres.CategoryType.bet -> {
                    ivSection.setBackgroundResource(R.drawable.ic_bet)
                }
            }
        }

        fun listener() = with(binding) {
            root.setOnClickListener {
                itemOnClick!!.invoke(currentList[adapterPosition].name)
            }
        }
    }

    companion object {
        private val paymentDiffCallback = object : DiffUtil.ItemCallback<PaymentPres>() {

            override fun areItemsTheSame(oldItem: PaymentPres, newItem: PaymentPres): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: PaymentPres, newItem: PaymentPres): Boolean {
                return oldItem == newItem
            }
        }
    }
}
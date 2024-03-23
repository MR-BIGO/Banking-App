package com.example.bankingapp.presentation.screen.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bankingapp.R
import com.example.bankingapp.databinding.CardRvItemBinding
import com.example.bankingapp.presentation.model.CardPres

class CardsRecyclerViewAdapter :
    ListAdapter<CardPres, CardsRecyclerViewAdapter.CardsViewHolder>(DiffCallback()) {

    var itemOnClick: ((String) -> Unit)? = null

    inner class CardsViewHolder(private val binding: CardRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() = with(binding) {
            val card = currentList[adapterPosition]
            tvAmountGel.text = card.amountGEL.toString().plus(" ")
                .plus(root.resources.getString(R.string.symbol_gel))
            tvAmountUsd.text = card.amountUSD.toString().plus(" ")
                .plus(root.resources.getString(R.string.symbol_usd))
            tvAmountEur.text = card.amountEUR.toString().plus(" ")
                .plus(root.resources.getString(R.string.symbol_eur))

            when (card.cardNum.first()) {
                '4' -> {
                    ivPaymentCorp.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            root.resources,
                            R.drawable.ic_visa,
                            root.resources.newTheme()
                        )
                    )
                }

                '5' -> {
                    ivPaymentCorp.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            root.resources,
                            R.drawable.ic_mastercard,
                            root.resources.newTheme()
                        )
                    )
                }

                else -> {}
            }
        }

        fun listener()= with(binding){
            root.setOnClickListener {
                itemOnClick!!.invoke(currentList[adapterPosition].id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardsViewHolder {
        return CardsViewHolder(
            CardRvItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    fun setData(cards: List<CardPres>) {
        submitList(cards)
    }

    override fun onBindViewHolder(holder: CardsViewHolder, position: Int) {
        holder.bind()
        holder.listener()
    }


    private class DiffCallback : DiffUtil.ItemCallback<CardPres>() {
        override fun areItemsTheSame(
            oldItem: CardPres, newItem: CardPres
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: CardPres, newItem: CardPres
        ): Boolean {
            return oldItem == newItem
        }
    }
}
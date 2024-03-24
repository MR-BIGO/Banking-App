package com.example.bankingapp.presentation.screen.transactions.cards_bottom_sheet.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bankingapp.R
import com.example.bankingapp.databinding.CardBottomsheetRvItemBinding
import com.example.bankingapp.presentation.model.CardPres

class CardsRecyclerAdapter :
    ListAdapter<CardPres, CardsRecyclerAdapter.CardsViewHolder>(DiffCallback()) {

    var itemOnClick: ((CardPres) -> Unit)? = null

    inner class CardsViewHolder(private val binding: CardBottomsheetRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() = with(binding) {
            val card = currentList[adapterPosition]
            tvGelAmount.text = card.amountGEL.toString().plus(" ")
                .plus(root.resources.getString(R.string.symbol_gel))

            tvLastFour.text = "**** ".plus(card.cardNum.takeLast(4))

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

                else -> {
                    ivPaymentCorp.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            root.resources,
                            R.drawable.ic_paypal,
                            root.resources.newTheme()
                        )
                    )
                }
            }
        }

        fun listener() = with(binding) {
            root.setOnClickListener {
                itemOnClick!!.invoke(currentList[adapterPosition])
            }
        }
    }

    fun setData(cards: List<CardPres>){
        submitList(cards)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardsViewHolder {
        return CardsViewHolder(
            CardBottomsheetRvItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
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
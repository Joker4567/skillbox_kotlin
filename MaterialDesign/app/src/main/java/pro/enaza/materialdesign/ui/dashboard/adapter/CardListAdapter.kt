package pro.enaza.materialdesign.ui.dashboard.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import pro.enaza.materialdesign.data.model.Card

class CardListAdapter: AsyncListDifferDelegationAdapter<Card>(MovieDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(CardDelegateAdapter())
    }

    class MovieDiffUtilCallback : DiffUtil.ItemCallback<Card>() {
        override fun areItemsTheSame(oldItem: Card, newItem: Card): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Card, newItem: Card): Boolean {
            return oldItem == newItem
        }
    }

}
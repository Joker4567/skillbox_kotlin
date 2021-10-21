package pro.enaza.materialdesign.ui.dashboard.adapter

import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_card.view.*
import pro.enaza.materialdesign.R
import pro.enaza.materialdesign.data.model.Card
import pro.enaza.materialdesign.utils.inflate

class CardDelegateAdapter: AbsListItemAdapterDelegate<Card, Card, CardDelegateAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(parent.inflate(R.layout.item_card))
    }

    override fun isForViewType(
            item: Card,
            items: MutableList<Card>,
            position: Int
    ): Boolean = true

    override fun onBindViewHolder(item: Card, holder: Holder, payloads: MutableList<Any>) {
        holder.bind(item)
    }

    class Holder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(item: Card) {
           containerView.card_title.text = item.title
            containerView.card_cost.text = "${item.cost}$"
            containerView.card_description.text = item.description
            Glide.with(itemView)
                    .load(item.posterUrl)
                    .into(containerView.card_image)
        }

    }
}
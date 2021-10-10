package com.skillbox.presentation.delegates

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import com.skillbox.presentation.R
import com.skillbox.presentation.lenta.model.LentaAdapterModel
import kotlinx.android.synthetic.main.item_lenta.view.*

fun itemTapeRecyclerView(
        itemClick:(Int) -> Unit
) = adapterDelegateLayoutContainer<LentaAdapterModel, Any>(R.layout.item_lenta) {

    itemView.setOnClickListener {
        itemClick.invoke(item.restaurantId)
    }

    bind {
        containerView.lenta_item_description.text = item.description
        containerView.lenta_item_title.text = item.title
        containerView.lenta_item_restourant.text = item.restaurant
    }
}
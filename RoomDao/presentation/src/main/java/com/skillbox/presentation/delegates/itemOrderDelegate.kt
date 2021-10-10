package com.skillbox.presentation.delegates

import android.view.View
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import com.skillbox.presentation.R
import com.skillbox.presentation.order.model.OrderAdapterModel
import kotlinx.android.synthetic.main.item_order.view.*

fun itemOrderRecyclerView(itemClick:(Int) -> Unit) = adapterDelegateLayoutContainer<OrderAdapterModel, Any>(R.layout.item_order) {

    itemView.setOnClickListener {
        itemClick.invoke(item.orderId)
    }

    bind {
        containerView.order_item_date.text = item.date
        containerView.order_item_price.text = "${item.price} руб."
        containerView.order_item_restaurant.text = item.restaurant
        if(item.discount > 0)
            containerView.order_item_sale.text = "${item.discount}%"
        else
            containerView.order_item_sale.visibility = View.INVISIBLE
    }
}
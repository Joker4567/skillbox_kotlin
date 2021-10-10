package com.skillbox.presentation.delegates

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import com.skillbox.presentation.R
import com.skillbox.presentation.rating.model.RatingAdapterModel
import kotlinx.android.synthetic.main.item_rating.view.*

fun itemRatingRecyclerView() = adapterDelegateLayoutContainer<RatingAdapterModel ,Any>(R.layout.item_rating) {

    bind {
        containerView.rating_item_description.text = item.description
        containerView.rating_item_title.text = item.title
        containerView.rating_item_restourant.text = item.restaurant
        containerView.rating_item_stars.text = "${item.stars}*"
    }
}
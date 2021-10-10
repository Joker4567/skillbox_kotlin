package com.skillbox.presentation.delegates

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import com.skillbox.data.db.entities.Menu
import com.skillbox.presentation.R
import kotlinx.android.synthetic.main.item_menu.view.*

fun itemMenuRecyclerView() = adapterDelegateLayoutContainer<Menu, Any>(R.layout.item_menu) {

    bind {
        containerView.menu_item_title.text = item.title
        containerView.menu_item_description.text = item.description
    }
}
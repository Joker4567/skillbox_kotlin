package com.example.contentprovider.UI.Adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.contentprovider.Data.Contact
import com.example.contentprovider.R
import com.example.contentprovider.Utils.inflate
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_contact.*


class ContactAdapterDelegate (
    private val onItemClick: (position: Int) -> Unit
) :
    AbsListItemAdapterDelegate<Contact, Contact, ContactAdapterDelegate.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(
            parent.inflate(R.layout.item_contact),
            onItemClick
        )
    }

    override fun isForViewType(
        item: Contact,
        items: MutableList<Contact>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onBindViewHolder(item: Contact, holder: Holder, payloads: MutableList<Any>) {
        holder.bind(item)
    }

    class Holder(
        override val containerView: View,
        onItemClick: (position: Int) -> Unit
    ) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        init {
            containerView.setOnClickListener {
                onItemClick(adapterPosition)
                true
            }
        }

        fun bind(item: Contact) {
            item_textViewName.text = item.name
            if(item.numbers.size == 0) item_textViewNumber.text = ""
            else item_textViewNumber.text = item.numbers.first()
            Glide.with(itemView)
                .load(item.avatar)
                .transform(CircleCrop())
                .placeholder(R.drawable.ic_baseline_image)
                .error(R.drawable.ic_baseline_image_not_supported)
                .into(item_image)
        }
    }
}
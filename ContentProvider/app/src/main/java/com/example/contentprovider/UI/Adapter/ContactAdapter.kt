package com.example.contentprovider.UI.Adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.contentprovider.Data.Contact
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class ContactAdapter (onItemClick: (position: Int) -> Unit ) : AsyncListDifferDelegationAdapter<Contact>(
    UserDiffUtilCallback()
) {

    init {
        delegatesManager.addDelegate(
            ContactAdapterDelegate(
                onItemClick
            )
        )
    }

    class UserDiffUtilCallback : DiffUtil.ItemCallback<Contact>() {
        override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return true
        }
    }
}
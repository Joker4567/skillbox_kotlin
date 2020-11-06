package com.skillbox.github.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.skillbox.github.R
import com.skillbox.github.models.Repositories
import com.skillbox.github.utils.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_repo.*

class RepositoriesAdapterDelegate(
    private val onItemClick: (position: Int) -> Unit
) :
    AbsListItemAdapterDelegate<Repositories, Repositories, RepositoriesAdapterDelegate.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(
            parent.inflate(R.layout.item_repo),
            onItemClick
        )
    }

    override fun isForViewType(
        item: Repositories,
        items: MutableList<Repositories>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onBindViewHolder(item: Repositories, holder: Holder, payloads: MutableList<Any>) {
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

        fun bind(item: Repositories) {
            repoIdTV.text = item.id.toString()
            repoNameTV.text = item.header
            repoDescTV.text = item.fullName
            Glide.with(itemView)
                .load(item.owner.avatar_url)
                .transform(CircleCrop())
                .placeholder(R.drawable.ic_baseline_photo_size_select_actual_24)
                .error(R.drawable.ic_baseline_image_not_supported_24)
                .into(ownerAvatarIV)
        }
    }
}
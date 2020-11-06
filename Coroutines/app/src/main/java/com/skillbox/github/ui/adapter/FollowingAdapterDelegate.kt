package com.skillbox.github.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.skillbox.github.R
import com.skillbox.github.models.Following
import com.skillbox.github.utils.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.folowing_item.*

class FollowingAdapterDelegate() :
    AbsListItemAdapterDelegate<Following, Following, FollowingAdapterDelegate.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(
            parent.inflate(R.layout.folowing_item))
    }

    override fun isForViewType(
        item: Following,
        items: MutableList<Following>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onBindViewHolder(
        item: Following,
        holder: FollowingAdapterDelegate.Holder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class Holder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bind(item: Following) {
            followingName.text = item.login
            Glide.with(itemView)
                .load(item.avatar_url)
                .transform(CircleCrop())
                .placeholder(R.drawable.ic_baseline_photo_size_select_actual_24)
                .error(R.drawable.ic_baseline_image_not_supported_24)
                .into(followingImage)
        }
    }
}
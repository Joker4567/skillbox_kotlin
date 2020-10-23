package com.skillbox.networking.movie_list

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.skillbox.networking.R
import com.skillbox.networking.utils.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_movie.*

class MovieDelegateAdapter: AbsListItemAdapterDelegate<RemoteMovie, RemoteMovie, MovieDelegateAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(parent.inflate(R.layout.item_movie))
    }

    override fun isForViewType(
        item: RemoteMovie,
        items: MutableList<RemoteMovie>,
        position: Int
    ): Boolean = true

    override fun onBindViewHolder(item: RemoteMovie, holder: Holder, payloads: MutableList<Any>) {
        holder.bind(item)
    }

    class Holder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(item: RemoteMovie) {
            titleTextView.text = item.title
            yearTextView.text = item.year + " г."
            typeTextView.text = item.type
            Glide.with(itemView)
                .load(item.url)
                .placeholder(R.drawable.ic_baseline_image_24)
                .error(R.drawable.ic_baseline_error_outline_24)
                .into(imageView)
        }

    }
}
package ru.skillbox.flow.ui.adapter

import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_movie.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.skillbox.flow.R
import ru.skillbox.flow.model.Movie
import ru.skillbox.flow.utils.inflate

class MovieDelegateAdapter: AbsListItemAdapterDelegate<Movie, Movie, MovieDelegateAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(parent.inflate(R.layout.item_movie))
    }

    override fun isForViewType(
            item: Movie,
            items: MutableList<Movie>,
            position: Int
    ): Boolean = true

    override fun onBindViewHolder(item: Movie, holder: Holder, payloads: MutableList<Any>) {
        holder.bind(item)
    }

    class Holder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(item: Movie) {
            containerView.titleTextView.text = item.title
            containerView.yearTextView.text = item.year + " г."
            containerView.typeTextView.text = item.type
            Glide.with(itemView)
                    .load(item.url)
                    .placeholder(R.drawable.ic_baseline_image_24)
                    .error(R.drawable.ic_baseline_error_outline_24)
                    .into(containerView.imageView)
        }

    }
}
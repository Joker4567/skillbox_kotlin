package ru.skillbox.scopedstorage.presentation.images.list

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import ru.skillbox.scopedstorage.R
import ru.skillbox.scopedstorage.data.Video
import ru.skillbox.scopedstorage.databinding.ItemVideoBinding
import ru.skillbox.scopedstorage.utils.inflate

class VideoAdapterDelegate(
    private val onDeleteVideo: (id: Long) -> Unit
) : AbsListItemAdapterDelegate<Video, Video, VideoAdapterDelegate.Holder>() {

    override fun isForViewType(item: Video, items: MutableList<Video>, position: Int): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return parent.inflate(ItemVideoBinding::inflate).let { Holder(it, onDeleteVideo) }
    }

    override fun onBindViewHolder(item: Video, holder: Holder, payloads: MutableList<Any>) {
        holder.bind(item)
    }

    class Holder(
        private val binding: ItemVideoBinding,
        onDeleteVideo: (id: Long) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private var currentVideoId: Long? = null

        init {
            binding.deleteButton.setOnClickListener {
                currentVideoId?.let(onDeleteVideo)
            }
        }

        fun bind(item: Video) {
            currentVideoId = item.id
            with(binding) {
                nameTextView.text = item.name
                sizeTextView.text = "${item.size} bytes"
                Glide.with(videoPreviewView)
                    .load(item.uri)
                    .transform(CircleCrop())
                    .placeholder(R.drawable.ic_video)
                    .into(videoPreviewView)
            }
        }
    }
}
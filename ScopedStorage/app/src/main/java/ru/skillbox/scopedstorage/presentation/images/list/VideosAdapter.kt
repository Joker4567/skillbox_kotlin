package ru.skillbox.scopedstorage.presentation.images.list

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.skillbox.scopedstorage.data.Video

class VideosAdapter(
    onDeleteVideo: (id: Long) -> Unit
): AsyncListDifferDelegationAdapter<Video>(ImageDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(VideoAdapterDelegate(onDeleteVideo))
    }

    class ImageDiffUtilCallback: DiffUtil.ItemCallback<Video>() {
        override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean {
            return oldItem == newItem
        }
    }

}
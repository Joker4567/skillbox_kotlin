package ru.skillbox.dependency_injection.presentation.images.list

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.skillbox.dependency_injection.data.Image

class ImagesAdapter(
    onDeleteImage: (id: Long) -> Unit
): AsyncListDifferDelegationAdapter<Image>(ImageDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(ImageAdapterDelegate(onDeleteImage))
    }

    class ImageDiffUtilCallback: DiffUtil.ItemCallback<Image>() {
        override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem == newItem
        }
    }

}
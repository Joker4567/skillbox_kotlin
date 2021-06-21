package ru.skillbox.dependency_injection.presentation.images.list

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import ru.skillbox.dependency_injection.R
import ru.skillbox.dependency_injection.data.Image
import ru.skillbox.dependency_injection.databinding.ItemImageBinding
import ru.skillbox.dependency_injection.utils.inflate

class ImageAdapterDelegate(
    private val onDeleteImage: (id: Long) -> Unit
) : AbsListItemAdapterDelegate<Image, Image, ImageAdapterDelegate.Holder>() {

    override fun isForViewType(item: Image, items: MutableList<Image>, position: Int): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return parent.inflate(ItemImageBinding::inflate).let { Holder(it, onDeleteImage) }
    }

    override fun onBindViewHolder(item: Image, holder: Holder, payloads: MutableList<Any>) {
        holder.bind(item)
    }

    class Holder(
        private val binding: ItemImageBinding,
        onDeleteImage: (id: Long) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private var currentImageId: Long? = null

        init {
            binding.deleteButton.setOnClickListener {
                currentImageId?.let(onDeleteImage)
            }
        }

        fun bind(item: Image) {
            currentImageId = item.id
            with(binding) {
                nameTextView.text = item.name
                sizeTextView.text = "${item.size} bytes"
                Glide.with(imageView)
                    .load(item.uri)
                    .transform(CircleCrop())
                    .placeholder(R.drawable.ic_image)
                    .into(imageView)
            }
        }
    }
}
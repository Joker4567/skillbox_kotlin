package com.skillbox.datetimepermission.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.skillbox.datetimepermission.Models.Message
import com.skillbox.datetimepermission.R
import com.skillbox.datetimepermission.Utils.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_message.*
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter

class MessageAdapter(
    private val onItemClick: (position: Int) -> Unit
): ListAdapter<Message, MessageAdapter.Holder>(MessageDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(parent.inflate(R.layout.item_message), onItemClick)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }

    class MessageDiffUtilCallback: DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem == newItem
        }
    }

    class Holder(
        override val containerView: View,
        onItemClick: (position: Int) -> Unit
    ): RecyclerView.ViewHolder(containerView), LayoutContainer {

        private val formatter = DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy")
            .withZone(ZoneId.systemDefault())

        fun bind(message: Message) {
            textDescription.text = message.text
            textDate.text = formatter.format(message.dateTime)
            Glide.with(itemView)
                .load("https://www.pngfind.com/pngs/m/377-3777765_location-clipart-geolocation-google-maps-hd-png-download.png")
                .placeholder(R.drawable.not_geo)
                .error(R.drawable.error_image)
                .into(imageView)
        }

        init {
            containerView.setOnClickListener {
                onItemClick(adapterPosition)
            }
        }
    }
}

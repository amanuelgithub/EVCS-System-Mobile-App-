package com.amanuel.evscsystem.ui.notification

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.amanuel.evscsystem.data.models.Notification
import com.amanuel.evscsystem.databinding.ItemNotificationBinding

class NotificationAdapter :
    ListAdapter<Notification, NotificationAdapter.NotificationsViewHolder>(NotificationDiffCallBack()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationsViewHolder {
        val binding =
            ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotificationsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotificationsViewHolder, position: Int) {
        val currentNotificationItem = getItem(position)
        holder.bind(currentNotificationItem)
    }

    class NotificationsViewHolder(private val binding: ItemNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(notification: Notification) {
            binding.apply {
                // @todo: bind the view with the notifications data
                speedTextView.setText(notification.speed)
            }
        }
    }

    class NotificationDiffCallBack : DiffUtil.ItemCallback<Notification>() {
        override fun areItemsTheSame(oldItem: Notification, newItem: Notification) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Notification, newItem: Notification) =
            oldItem == newItem

    }

}
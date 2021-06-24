package com.amanuel.evscsystem.ui.notification

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.amanuel.evscsystem.data.models.Notification
import com.amanuel.evscsystem.databinding.ItemNotificationBinding

class NotificationAdapter(
    private val listener: onLocationImageViewClickListener
) :
    ListAdapter<Notification, NotificationAdapter.NotificationsViewHolder>(NotificationDiffCallBack()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationsViewHolder {
        val binding =
            ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotificationsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotificationsViewHolder, position: Int) {
        val currentNotificationItem = getItem(position)
        holder.bind(currentNotificationItem)
        holder.apply {

        }
    }

        inner class NotificationsViewHolder(private val binding: ItemNotificationBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        fun bind(notification: Notification) {
            binding.apply {
                // @todo: bind the view with the notifications data
                plateNoTextView.setText(notification.plateNumber.toString())
                // include vehicle code
                vehicleSpeedTextView.setText(notification.speed.toString())
                locationTextView.setText(notification.location.toString())
            }
        }

        init {
            binding.locationImageView.setOnClickListener(this)
            binding.moreImageView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION){

                when(v){
                    binding.locationImageView ->{
                        val notificationId  = getItem(position).id
                        val notificationPlateNumber = getItem(position).plateNumber

                        listener.onLocationImageViewClick(notificationId, notificationPlateNumber)
                    }
                    binding.moreImageView ->{
                        // to handle the more options(this is done by showing a bottom sheet in the NotificationsFragment)
                        listener.onMoreOptionClick(position)
                    }
                }
            }
        }
    }

    class NotificationDiffCallBack : DiffUtil.ItemCallback<Notification>() {
        override fun areItemsTheSame(oldItem: Notification, newItem: Notification) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Notification, newItem: Notification) =
            oldItem == newItem

    }

    interface onLocationImageViewClickListener{
        fun onLocationImageViewClick(notificationId: Int, notificationPlateNumber: Int)

        fun onMoreOptionClick(position: Int)
    }

}
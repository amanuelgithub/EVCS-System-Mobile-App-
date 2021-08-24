package com.amanuel.evscsystem.ui.notification

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.amanuel.evscsystem.data.db.models.Notification
import com.amanuel.evscsystem.databinding.ItemNotificationBinding

class NotificationAdapter(
    private val notificationWidgetsClickListenerInterface: NotificationWidgetsClickListenerInterface
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
    }

    inner class NotificationsViewHolder(private val binding: ItemNotificationBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {


        fun bind(notification: Notification) {
            binding.apply {
                // @todo: bind the view with the notifications data
                plateNoTextView.text = notification.plate_number.toString()
                latitudeTextView.text = "${notification.latitude} \n &#176"
                longitudeTextView.text = "${notification.longtude} \n &#176"
                vehicleSpeedTextView.text = "${notification.vehicle_speed} km/s"
            }
        }

        // setting up the clickListener for the NotificationListItem views or widgets
        init {
            binding.locationImageView.setOnClickListener(this)
            binding.notificationConstraintLayout.setOnClickListener(this)
        }

        // This onclick listener listen for the multiple click events for that
        // for the different views or widgets in the recyclerView.
        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {

                when (v) {
                    // when the LocationImageView ,which is contained in the cardView, is
                    // clicked it will send the the notification id to the method that
                    // handles the locationImageViewClicking
                    binding.notificationConstraintLayout -> {
                        val notification: Notification = getItem(position) as Notification
                        notificationWidgetsClickListenerInterface.onNotificationCardViewClicked(
                            notification
                        )
                    }

                    binding.locationImageView -> {
                        val notification: Notification = getItem(position) as Notification
                        notificationWidgetsClickListenerInterface
                            .onLocationImageViewClicked(notification)
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

    interface NotificationWidgetsClickListenerInterface {
        fun onLocationImageViewClicked(notification: Notification)

        fun onNotificationCardViewClicked(notification: Notification)
    }

}
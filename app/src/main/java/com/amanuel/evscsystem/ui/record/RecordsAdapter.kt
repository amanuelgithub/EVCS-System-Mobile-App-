package com.amanuel.evscsystem.ui.record

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.amanuel.evscsystem.data.db.models.Record
import com.amanuel.evscsystem.databinding.RecyclerViewRecordItemBinding

class RecordsAdapter : ListAdapter<Record, RecordsAdapter.RecordsViewHolder>(RecordDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordsViewHolder {
        val binding =
            RecyclerViewRecordItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecordsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecordsViewHolder, position: Int) {
        val currentRecordItem = getItem(position)
        holder.bind(currentRecordItem)
    }

    inner class RecordsViewHolder(private val binding: RecyclerViewRecordItemBinding) :
        RecyclerView.ViewHolder(binding.root){

        fun bind(record: Record) {
            binding.apply {
                latitudeRecordTextView.text = record.latitude.toString()
                longitudeRecordTextView.text = record.longitude.toString()
                vehicleSpeedRecordTextView.text = record.vehicleSpeed.toString()
                addressRecordTextView.text = record.address
                createdAtRecordTextView.text = record.createdAt
            }
        }
    }

    class RecordDiffCallBack: DiffUtil.ItemCallback<Record>() {
        override fun areItemsTheSame(oldItem: Record, newItem: Record): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Record, newItem: Record): Boolean {
            return oldItem == newItem
        }

    }


}
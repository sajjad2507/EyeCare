package com.example.eyecare.ui.reminder.reminderlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.eyecare.databinding.ReminderItemBinding
import com.example.eyecare.ui.utils.roomdb.model.RemindersModel

class ReminderAdapter() :
    ListAdapter<RemindersModel, ReminderAdapter.ReminderViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        val binding = ReminderItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ReminderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, position)
    }
    inner class ReminderViewHolder(private val binding: ReminderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RemindersModel, position: Int) {
            binding.apply {
                reminderHeading.text = item.reminderTitle
                reminderDesc.text = item.reminderDesc
            }

        }
    }



    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RemindersModel>() {

            override fun areItemsTheSame(
                oldItem: RemindersModel,
                newItem: RemindersModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: RemindersModel,
                newItem: RemindersModel
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}
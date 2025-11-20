package com.uilover.project2002.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.uilover.project2002.R
import com.uilover.project2002.databinding.ItemTimeBinding

class TimeAdapter(
    private val timeSlots: List<String>,
    private val listener: OnTimeSelectedListener
) : RecyclerView.Adapter<TimeAdapter.TimeViewholder>() {

    private var selectedPosition = -1
    private var lastSelectedPosition = -1

    interface OnTimeSelectedListener {
        fun onTimeSelected(position: Int, time: String)
    }

    inner class TimeViewholder(private val binding: ItemTimeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(time: String, position: Int) {
            binding.TextViewTime.text = time

            if (selectedPosition == position) {
                binding.TextViewTime.setBackgroundResource(R.drawable.white_bg)
                binding.TextViewTime.setTextColor(
                    ContextCompat.getColor(itemView.context, R.color.black)
                )
            } else {
                binding.TextViewTime.setBackgroundResource(R.drawable.light_black_bg)
                binding.TextViewTime.setTextColor(
                    ContextCompat.getColor(itemView.context, R.color.white)
                )
            }

            binding.root.setOnClickListener {
                val currentPosition = adapterPosition
                if (currentPosition != RecyclerView.NO_POSITION) {
                    lastSelectedPosition = selectedPosition
                    selectedPosition = currentPosition
                    notifyItemChanged(lastSelectedPosition)
                    notifyItemChanged(selectedPosition)
                    listener.onTimeSelected(currentPosition, time)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeViewholder {
        return TimeViewholder(
            ItemTimeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TimeViewholder, position: Int) {
        holder.bind(timeSlots[position], position)
    }

    override fun getItemCount(): Int = timeSlots.size
}

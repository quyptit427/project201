package com.uilover.project2002.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.uilover.project2002.R
import com.uilover.project2002.databinding.ItemDateBinding

class DateAdapter(
    private val dates: List<String>,
    private val listener: OnDateSelectedListener
) : RecyclerView.Adapter<DateAdapter.DateViewholder>() {

    private var selectedPosition = -1
    private var lastSelectedPosition = -1

    interface OnDateSelectedListener {
        fun onDateSelected(position: Int, formattedDate: String)
    }

    inner class DateViewholder(private val binding: ItemDateBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(date: String, position: Int) {
            val dateParts = date.split("/")
            if (dateParts.size == 3) {
                binding.dayTxt.text = dateParts[0]
                binding.datMonthTxt.text = dateParts[1] + " " + dateParts[2]

                if (selectedPosition == position) {
                    binding.mailLayout.setBackgroundResource(R.drawable.white_bg)
                    binding.dayTxt.setTextColor(ContextCompat.getColor(itemView.context, R.color.black))
                    binding.datMonthTxt.setTextColor(ContextCompat.getColor(itemView.context, R.color.black))
                } else {
                    binding.mailLayout.setBackgroundResource(R.drawable.light_black_bg)
                    binding.dayTxt.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
                    binding.datMonthTxt.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
                }

                binding.root.setOnClickListener {
                    val currentPosition = adapterPosition
                    if (currentPosition != RecyclerView.NO_POSITION) {
                        lastSelectedPosition = selectedPosition
                        selectedPosition = currentPosition
                        notifyItemChanged(lastSelectedPosition)
                        notifyItemChanged(selectedPosition)
                        listener.onDateSelected(currentPosition, date)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewholder {
        return DateViewholder(
            ItemDateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DateViewholder, position: Int) {
        holder.bind(dates[position], position)
    }

    override fun getItemCount(): Int = dates.size
}
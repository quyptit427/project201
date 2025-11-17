package com.uilover.project2002.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uilover.project2002.Models.Seat
import com.uilover.project2002.R
import com.uilover.project2002.databinding.SeatItemBinding

class SeatListAdapter(
    private val seatList: List<Seat>,
    private val context: Context,
    private val selectedSeat: SelectedSeat
) : RecyclerView.Adapter<SeatListAdapter.SeatViewholder>() {

    private val selectedSeatName = ArrayList<String>()

    class SeatViewholder(val binding: SeatItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeatViewholder {
        return SeatViewholder(
            SeatItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SeatViewholder, position: Int) {
        val seat = seatList[position]
        holder.binding.seat.text = seat.name

        when (seat.status) {
            Seat.SeatStatus.AVAILABLE -> {
                holder.binding.seat.setBackgroundResource(R.drawable.ic_seat_available)
                holder.binding.seat.setTextColor(context.getColor(R.color.white))
            }
            Seat.SeatStatus.SELECTED -> {
                holder.binding.seat.setBackgroundResource(R.drawable.ic_seat_selected)
                holder.binding.seat.setTextColor(context.getColor(R.color.black))
            }
            Seat.SeatStatus.UNAVAILABLE -> {
                holder.binding.seat.setBackgroundResource(R.drawable.ic_seat_unavailable)
                holder.binding.seat.setTextColor(context.getColor(R.color.grey))
            }
        }

        holder.binding.seat.setOnClickListener {
            when (seat.status) {
                Seat.SeatStatus.AVAILABLE -> {
                    seat.status = Seat.SeatStatus.SELECTED
                    selectedSeatName.add(seat.name)
                    notifyItemChanged(position)
                }
                Seat.SeatStatus.SELECTED -> {
                    seat.status = Seat.SeatStatus.AVAILABLE
                    selectedSeatName.remove(seat.name)
                    notifyItemChanged(position)
                }
                else -> {
                    // Ghế không khả dụng - không làm gì
                }
            }
            val selected = selectedSeatName.joinToString(",")
            selectedSeat.Return(selected, selectedSeatName.size)
        }
    }

    override fun getItemCount(): Int = seatList.size

    // Thêm method để update trạng thái ghế từ server
    fun updateSeatStatus(bookedSeats: List<String>, lockedSeats: List<String>) {
        seatList.forEachIndexed { index, seat ->
            val seatNumber = (index + 1).toString()

            when {
                bookedSeats.contains(seatNumber) -> {
                    seat.status = Seat.SeatStatus.UNAVAILABLE
                }
                lockedSeats.contains(seatNumber) -> {
                    // Ghế đang bị lock bởi user khác
                    seat.status = Seat.SeatStatus.UNAVAILABLE
                }
                else -> {
                    // Chỉ set AVAILABLE nếu chưa được user hiện tại chọn
                    if (!selectedSeatName.contains(seat.name)) {
                        seat.status = Seat.SeatStatus.AVAILABLE
                    }
                }
            }
        }
        notifyDataSetChanged()
    }

    interface SelectedSeat {
        fun Return(selectedName: String, num: Int)
    }
}
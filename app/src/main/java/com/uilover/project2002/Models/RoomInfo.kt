package com.uilover.project2002.Models

data class RoomInfo(
    val name: String = "",
    val totalSeats: Int = 81,
    val rows: Int = 9,
    val seatsPerRow: Int = 9,
    val seatLayout: List<String?> = emptyList()
)
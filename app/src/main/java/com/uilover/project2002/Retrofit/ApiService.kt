package com.uilover.project2002.Retrofit



import com.uilover.project2002.Models.Seat
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("api/showtime/{id}/seats")
    suspend fun getSeatsByShowtime(
        @Path("id") showtimeId: Long
    ): List<Seat>
}

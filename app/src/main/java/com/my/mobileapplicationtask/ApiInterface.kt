package com.my.mobileapplicationtask

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {
    @Headers("Content-Type: application/json")
    @POST("login")
    fun postData(@Body users:UserData): Call<loginData>
}

interface RegisterApiInterface {
    @Headers("Content-Type: application/json")
    @POST("register")
    fun postRegData(@Body reg:UserData): Call<RegisterDataClass>
}
interface HotelsApiService {
    @GET("/hotelBooking/hotels")
    suspend fun GetHotels(@Header("Authorization") token: String): Response<HotelList>

    @POST("/hotelBooking/users/me/reservations")
    suspend fun PlaceHotelReservations(
        @Header("Authorization") token: String,
        @Body request: Hotels
    ): Response<Unit>



}
interface HistoryApiService {
    @GET("/hotelBooking/users/me/loginHistory")
    suspend fun logDetails(@Header("Authorization") token: String): Response<LogList>
}

interface OtherUserApiService {
    @GET("/hotelBooking/users")
    suspend fun otherUserDetails(@Header("Authorization") token: String): Response<otherUserList>
}

interface ChangeEmailApiService {
    @Headers("Content-Type: application/json")

    @POST("/hotelBooking/users/me/email")
    fun ChangeEmail(@Header("Authorization") token: String, @Body user: EmailUpdate): Call<Void>

}

interface DeleteApiService {
    @Headers("Content-Type: application/json")
    @DELETE("/hotelBooking/users/me")
    fun deleteUser(@Header("Authorization") token: String): Call<Void>
    @DELETE("hotelBooking/users/me/reservations/{reservationId}")
    suspend fun DeleteReservations(@Header("Authorization") token: String, @Path("reservationId")
    owningId:Int): Response<Unit>

}
interface  MyReservationsApiService {

    @GET("priceapp-secure-backend/users/me/ownings")
    suspend fun fetchReservations(@Header("Authorization") token: String): Response<MyHotelList>
}
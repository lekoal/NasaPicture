package com.example.nasapicture.repository

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PictureOfTheDayAPI {
    @GET("planetary/apod")
    fun getPictureOfTheDay(@Query("api_key") apiKey: String): Call<PictureOfTheDayDTO>

    @GET("planetary/apod")
    fun getPictureOfTheDayForDate(
        @Query("date") date: String,
        @Query("api_key") apiKey: String
    ): Call<PictureOfTheDayDTO>

    @GET("asset/{nasaId}")
    fun getNasaImage(
        @Path("nasaId") nasaId: String
    ): Call<NasaImageDTO>
}
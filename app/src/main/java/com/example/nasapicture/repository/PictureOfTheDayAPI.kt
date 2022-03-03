package com.example.nasapicture.repository

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PictureOfTheDayAPI {
    @GET("planetary/apod")
    fun getPictureOfTheDay(@Query("api_key") apiKey: String): Call<PictureOfTheDayDTO>

    @GET("planetary/apod")
    fun getPictureOfTheDayForDate(
        @Query("date") date: String,
        @Query("api_key") apiKey: String
    ): Call<PictureOfTheDayDTO>

    @GET("asset/")
    fun getNasaImage(
        @Query("nasa_id") nasaId: String
    ): Call<NasaImageDTO>
}
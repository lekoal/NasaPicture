package com.example.nasapicture.repository

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PictureOfTheDayRetrofitImpl {
    private val baseUrl = "https://api.nasa.gov/"
    private val baseImageUrl = "https://images-api.nasa.gov/"

    fun getRetrofitImpl(): PictureOfTheDayAPI {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
        return retrofit.create(PictureOfTheDayAPI::class.java)
    }

    fun getImageRetrofitImpl(): PictureOfTheDayAPI {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseImageUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
        return retrofit.create(PictureOfTheDayAPI::class.java)
    }
}
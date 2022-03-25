package com.example.nasapicture.viewmodel

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nasapicture.BuildConfig
import com.example.nasapicture.R
import com.example.nasapicture.repository.NasaImageDTO
import com.example.nasapicture.repository.PictureOfTheDayDTO
import com.example.nasapicture.repository.PictureOfTheDayRetrofitImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PictureOfTheDayViewModel(
    private val liveData: MutableLiveData<PictureOfTheDayState> = MutableLiveData(),
    private val pictureOfTheDayRetrofitImpl: PictureOfTheDayRetrofitImpl = PictureOfTheDayRetrofitImpl()
) : ViewModel() {

    fun getLiveData(): LiveData<PictureOfTheDayState> {
        return liveData
    }

    fun sendServerRequest() {
        liveData.postValue(PictureOfTheDayState.Loading(null))
        pictureOfTheDayRetrofitImpl.getRetrofitImpl().getPictureOfTheDay(BuildConfig.NASA_API_KEY)
            .enqueue(
                object : Callback<PictureOfTheDayDTO> {
                    override fun onResponse(
                        call: Call<PictureOfTheDayDTO>,
                        response: Response<PictureOfTheDayDTO>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            response.body()?.let {
                                liveData.postValue(PictureOfTheDayState.Success(it))
                            }
                        } else {
                            liveData.postValue(
                                PictureOfTheDayState.Error(
                                    Exception(
                                        Resources.getSystem().getString(R.string.live_data_error)
                                    )
                                )
                            )
                        }
                    }

                    override fun onFailure(call: Call<PictureOfTheDayDTO>, t: Throwable) {
                        liveData.postValue(PictureOfTheDayState.Error(t))
                    }

                }
            )
    }

    fun sendServerRequestForDate(date: String) {
        liveData.postValue(PictureOfTheDayState.Loading(null))
        pictureOfTheDayRetrofitImpl.getRetrofitImpl().getPictureOfTheDayForDate(date, BuildConfig.NASA_API_KEY)
            .enqueue(
                object : Callback<PictureOfTheDayDTO> {
                    override fun onResponse(
                        call: Call<PictureOfTheDayDTO>,
                        response: Response<PictureOfTheDayDTO>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            response.body()?.let {
                                liveData.postValue(PictureOfTheDayState.Success(it))
                            }
                        } else {
                            liveData.postValue(
                                PictureOfTheDayState.Error(
                                    Exception(
                                        Resources.getSystem().getString(R.string.live_data_error)
                                    )
                                )
                            )
                        }
                    }

                    override fun onFailure(call: Call<PictureOfTheDayDTO>, t: Throwable) {
                        liveData.postValue(PictureOfTheDayState.Error(t))
                    }

                }
            )
    }

    fun sendServerRequestForImage(nasaId: String) {
        liveData.postValue(PictureOfTheDayState.Loading(null))
        pictureOfTheDayRetrofitImpl.getImageRetrofitImpl().getNasaImage(nasaId)
            .enqueue(
                object : Callback<NasaImageDTO> {
                    override fun onResponse(
                        call: Call<NasaImageDTO>,
                        response: Response<NasaImageDTO>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            response.body()?.let {
                                liveData.postValue(PictureOfTheDayState.SuccessImage(it))
                            }
                        } else {
                            liveData.postValue(
                                PictureOfTheDayState.Error(
                                    Exception(
                                        R.string.live_data_error.toString()
                                    )
                                )
                            )
                        }
                    }

                    override fun onFailure(call: Call<NasaImageDTO>, t: Throwable) {
                        liveData.postValue(PictureOfTheDayState.Error(t))
                    }

                }
            )
    }

}
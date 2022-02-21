package com.example.nasapicture.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nasapicture.BuildConfig
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

    fun sendServerRequest(){
        liveData.postValue(PictureOfTheDayState.Loading(null))
        pictureOfTheDayRetrofitImpl.getRetrofitImpl().getPictureOfTheDay(BuildConfig.NASA_API_KEY).enqueue(
            object : Callback<PictureOfTheDayDTO> {
                override fun onResponse(
                    call: Call<PictureOfTheDayDTO>,
                    response: Response<PictureOfTheDayDTO>
                ) {
                    if(response.isSuccessful&&response.body()!=null){
                        response.body()?.let {
                            liveData.postValue(PictureOfTheDayState.Success(it))
                        }
                    }else{
                        //TODO HW
                    }
                }

                override fun onFailure(call: Call<PictureOfTheDayDTO>, t: Throwable) {
                    //TODO HW
                }

            }
        )
    }

}
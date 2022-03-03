package com.example.nasapicture.viewmodel

import com.example.nasapicture.repository.NasaImageDTO
import com.example.nasapicture.repository.PictureOfTheDayDTO

sealed class PictureOfTheDayState {
    data class Success(val serverResponseData: PictureOfTheDayDTO) : PictureOfTheDayState()
    data class SuccessImage(val serverResponseImageData: NasaImageDTO) : PictureOfTheDayState()
    data class Error(val error: Throwable) : PictureOfTheDayState()
    data class Loading(val progress: Int?) : PictureOfTheDayState()
}

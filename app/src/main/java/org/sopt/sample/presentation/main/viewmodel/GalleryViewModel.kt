package org.sopt.sample.presentation.main.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.sopt.sample.data.remote.api.ServicePool
import org.sopt.sample.presentation.main.view.MainActivity.Companion.tag
import org.sopt.sample.util.ContentUriRequestBody
import retrofit2.Callback
import retrofit2.Response

class GalleryViewModel : ViewModel() {
    private val service = ServicePool.imageService
    private val _image = MutableLiveData<ContentUriRequestBody>()
    val image: LiveData<ContentUriRequestBody>
        get() = _image

    private val _result = MutableLiveData<Int>()
    val result: LiveData<Int>
        get() = _result

    fun setRequestBody(requestBody: ContentUriRequestBody) {
        _image.value = requestBody
    }

    fun uploadProfileImage() {
        if (image.value == null) {
            Log.d(tag, "image is null ....")
        } else {
            service.uploadImage(0, image.value!!.toFormData())
                .enqueue(object : Callback<Unit> {
                    override fun onResponse(call: retrofit2.Call<Unit>, response: Response<Unit>) {
                        if (response.isSuccessful) {
                            _result.value = response.code()
                        } else {
                            _result.value = response.code()
                        }
                    }

                    override fun onFailure(call: retrofit2.Call<Unit>, t: Throwable) {
                        Log.d(tag, "네트워크 연결 환경이 좋지 않습니다....")
                    }

                })
        }
    }
}


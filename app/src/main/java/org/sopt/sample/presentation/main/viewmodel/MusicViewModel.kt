package org.sopt.sample.presentation.main.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.RequestBody
import org.sopt.sample.data.remote.api.ServicePool
import org.sopt.sample.data.remote.model.ResponseGetMusicDto
import org.sopt.sample.presentation.main.view.MainActivity
import org.sopt.sample.presentation.main.view.MainActivity.Companion.tag
import org.sopt.sample.util.ContentUriRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MusicViewModel : ViewModel() {
    private var _musicList: MutableLiveData<List<ResponseGetMusicDto.Music>> = MutableLiveData()
    val musicList: LiveData<List<ResponseGetMusicDto.Music>>
        get() = _musicList

    private var _getMusicResult: MutableLiveData<Int> = MutableLiveData()
    val getMusicResult: LiveData<Int>
        get() = _getMusicResult

    private var _registerMusicResult: MutableLiveData<Int> = MutableLiveData()
    val registerMusicResult: LiveData<Int>
        get() = _registerMusicResult

    private val musicService = ServicePool.musicService

    fun getMusicList() {
        musicService.getMusicList().enqueue(object : Callback<ResponseGetMusicDto> {
            override fun onResponse(
                call: Call<ResponseGetMusicDto>,
                response: Response<ResponseGetMusicDto>
            ) {
                if (response.isSuccessful) {
                    if (response.body()?.data != null)
                        _musicList.value = response.body()?.data!!
                    else {
                        Log.d(tag, "서버에서 보내준 음악 리스트가 null 입니다.")
                    }
                } else {
                    _getMusicResult.value = response.code()
                }
            }

            override fun onFailure(call: Call<ResponseGetMusicDto>, t: Throwable) {
                Log.d(MainActivity.tag, "네트워크 환경이 좋지 않습니다.")
            }

        })
    }

    fun registerMusic(requestBody: ContentUriRequestBody, request: RequestBody) {
        musicService.uploadMusic(requestBody.toFormData(), request)
            .enqueue(object : Callback<Unit> {
                override fun onResponse(
                    call: Call<Unit>,
                    response: Response<Unit>
                ) {
                    _registerMusicResult.value = response.code()
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    Log.d(MainActivity.tag, "네트워크 환경이 좋지 않습니다.")
                }
            })
    }
}
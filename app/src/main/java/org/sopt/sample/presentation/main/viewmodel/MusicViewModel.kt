package org.sopt.sample.presentation.main.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.sopt.sample.data.remote.api.ServicePool
import org.sopt.sample.data.remote.model.ResponseGetMusicDto
import org.sopt.sample.presentation.main.view.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MusicViewModel : ViewModel() {
    private var _musicList: MutableLiveData<List<ResponseGetMusicDto.Music>> = MutableLiveData()
    val musicList: LiveData<List<ResponseGetMusicDto.Music>>
        get() = _musicList

    private var _result: MutableLiveData<Int> = MutableLiveData()
    val result: LiveData<Int>
        get() = _result

    private val musicService = ServicePool.musicService

    fun getMusicList() {
        musicService.getMusicList().enqueue(object : Callback<ResponseGetMusicDto> {
            override fun onResponse(
                call: Call<ResponseGetMusicDto>,
                response: Response<ResponseGetMusicDto>
            ) {
                if (response.isSuccessful) {
                    _musicList.value = response.body()?.data!!
                } else {
                    _result.value = response.code()
                }
            }

            override fun onFailure(call: Call<ResponseGetMusicDto>, t: Throwable) {
                Log.d(MainActivity.tag, "네트워크 환경이 좋지 않습니다.")
            }

        })
    }
}
package org.sopt.sample.presentation.main.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.sopt.sample.data.remote.api.ServicePool
import org.sopt.sample.data.remote.model.ResponseGetUserDto
import org.sopt.sample.presentation.main.view.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    private var _userList: MutableLiveData<ResponseGetUserDto> = MutableLiveData()
    val userList: LiveData<ResponseGetUserDto>
        get() = _userList

    private var _errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: LiveData<Int>
        get() = _errorMessage

    private val getUserService = ServicePool.getUserService

    fun getUser() {
        getUserService.getUsers(2).enqueue(object : Callback<ResponseGetUserDto> {
            override fun onResponse(
                call: Call<ResponseGetUserDto>,
                response: Response<ResponseGetUserDto>
            ) {
                if (response.isSuccessful) {
                    _userList.value = response.body()
                } else {
                    _errorMessage.value = response.code()
                }
            }

            override fun onFailure(call: Call<ResponseGetUserDto>, t: Throwable) {
                Log.d(MainActivity.tag, "네트워크 환경이 좋지 않습니다.")
            }

        })
    }
}
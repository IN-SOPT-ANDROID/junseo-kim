package org.sopt.sample.presentation.login.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.sopt.sample.data.remote.api.ServicePool
import org.sopt.sample.data.remote.model.RequestLoginDTO
import org.sopt.sample.data.remote.model.ResponseLoginDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {
    private val _loginResult: MutableLiveData<ResponseLoginDTO> = MutableLiveData()
    val loginResult: MutableLiveData<ResponseLoginDTO>
        get() = _loginResult
    private val _errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int>
        get() = _errorMessage

    private val loginService = ServicePool.authService

    fun login(id: String, pw: String) {
        loginService.login(RequestLoginDTO(id, pw))
            .enqueue(object : Callback<ResponseLoginDTO> {
                override fun onResponse(
                    call: Call<ResponseLoginDTO>,
                    response: Response<ResponseLoginDTO>
                ) {
                    if (response.isSuccessful) {
                        _loginResult.value = response.body()
                    } else {
                        _errorMessage.value = response.body()?.status
                    }
                }

                override fun onFailure(call: Call<ResponseLoginDTO>, t: Throwable) {
                }

            })
    }
}
package org.sopt.sample.presentation.signup.viewModel

import android.util.Log
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.sopt.sample.data.remote.api.ServicePool
import org.sopt.sample.data.remote.model.RequestSignUpDTO
import org.sopt.sample.data.remote.model.ResponseSignUpDTO
import org.sopt.sample.databinding.ActivitySignUpBinding
import org.sopt.sample.presentation.main.view.MainActivity.Companion.tag
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpViewModel() : ViewModel() {
    private val _signUpResult: MutableLiveData<ResponseSignUpDTO> = MutableLiveData()
    val signUpResult: LiveData<ResponseSignUpDTO>
        get() = _signUpResult
    private val _errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: LiveData<Int>
        get() = _errorMessage

    private val _userId: MutableLiveData<String> = MutableLiveData()
    val userId: LiveData<String>
        get() = _userId
    private val _userPw: MutableLiveData<String> = MutableLiveData()
    val userPw: LiveData<String>
        get() = _userPw


    private val loginService = ServicePool.authService


    fun signUp(id: String, pw: String, name: String) {
        loginService.signUp(
            RequestSignUpDTO(
                id, pw, name
            )
        ).enqueue(object : Callback<ResponseSignUpDTO> {
            override fun onResponse(
                call: Call<ResponseSignUpDTO>,
                response: Response<ResponseSignUpDTO>
            ) {
                if (response.isSuccessful) {
                    _signUpResult.value = response.body()
                } else {
                    _errorMessage.value = response.code()
                }
            }

            override fun onFailure(call: Call<ResponseSignUpDTO>, t: Throwable) {
                Log.d(tag, "네트워크 환경이 좋지 않습니다.")
            }
        })
    }

    fun setTextWatcher(binding: ActivitySignUpBinding) {
        binding.etId.addTextChangedListener {
            _userId.value = it.toString()
        }
        binding.etPw.addTextChangedListener {
            _userPw.value = it.toString()
        }
    }
}
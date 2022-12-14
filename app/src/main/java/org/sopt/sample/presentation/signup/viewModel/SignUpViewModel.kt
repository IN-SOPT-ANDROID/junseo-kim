package org.sopt.sample.presentation.signup.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import org.sopt.sample.data.remote.api.ServicePool
import org.sopt.sample.data.remote.model.RequestSignUpDTO
import org.sopt.sample.data.remote.model.ResponseSignUpDTO
import org.sopt.sample.presentation.main.view.MainActivity.Companion.idPattern
import org.sopt.sample.presentation.main.view.MainActivity.Companion.pwPattern
import org.sopt.sample.presentation.main.view.MainActivity.Companion.tag
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class SignUpViewModel() : ViewModel() {
    private val _signUpResult: MutableLiveData<ResponseSignUpDTO> = MutableLiveData()
    val signUpResult: LiveData<ResponseSignUpDTO>
        get() = _signUpResult
    private val _errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: LiveData<Int>
        get() = _errorMessage

    val userIdText: MutableLiveData<String> = MutableLiveData("")
    val isUserIdSuit: LiveData<Boolean> = Transformations.map(userIdText) { checkId(it) }

    val userPwText: MutableLiveData<String> = MutableLiveData("")
    val isUserPwSuit: LiveData<Boolean> = Transformations.map(userPwText) { checkPw(it) }

    val userNameText: MutableLiveData<String> = MutableLiveData("")
    val isUserNameSuit: LiveData<Boolean> = Transformations.map(userNameText) { checkName(it) }

    val btnEnabled: MutableLiveData<Boolean> = MutableLiveData(false)

    private val authService = ServicePool.authService

    fun signUp(id: String, pw: String, name: String) {
        authService.signUp(
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
                Log.d(tag, "???????????? ????????? ?????? ????????????.")
            }
        })
    }

    private fun checkId(idText: String): Boolean {
        if (idText == "") {
            return true
        }
        return Pattern.compile(idPattern).matcher(idText).matches()
    }

    private fun checkPw(pwText: String): Boolean {
        if (pwText == "") {
            return true
        }
        return Pattern.compile(pwPattern).matcher(pwText).matches()
    }

    private fun checkName(nameText: String): Boolean {
        return nameText != ""
    }

    fun checkSignUpFormat(): Boolean {
        return (isUserIdSuit.value == true && isUserPwSuit.value == true
                && isUserNameSuit.value == true
                && userIdText.value!!.isNotEmpty()
                && userPwText.value!!.isNotEmpty())
    }

}
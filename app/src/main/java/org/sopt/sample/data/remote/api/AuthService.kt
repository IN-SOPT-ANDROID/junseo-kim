package org.sopt.sample.data.remote.api

import org.sopt.sample.data.remote.model.RequestLoginDto
import org.sopt.sample.data.remote.model.RequestSignUpDto
import org.sopt.sample.data.remote.model.ResponseLoginDto
import org.sopt.sample.data.remote.model.ResponseSignUpDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("api/user/signin")
    fun login(@Body request: RequestLoginDto): Call<ResponseLoginDto>

    @POST("api/user/signup")
    fun signUp(@Body request: RequestSignUpDto): Call<ResponseSignUpDto>
}
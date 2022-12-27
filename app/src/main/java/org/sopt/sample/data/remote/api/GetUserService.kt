package org.sopt.sample.data.remote.api

import org.sopt.sample.data.remote.model.ResponseGetUserDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GetUserService {
    @GET("api/users")
    fun getUsers(
        @Query("page")
        page: Int
    ): Call<ResponseGetUserDto>
}
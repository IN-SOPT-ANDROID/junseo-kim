package org.sopt.sample.data.remote

import retrofit2.Call
import retrofit2.http.GET

interface GetUserService{
    @GET("api/users?page=2")
    fun getUsers() : Call<ResponseGetUsersDTO>
}
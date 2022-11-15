package org.sopt.sample.data.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GetUserService{
    @GET("api/users?page=2")
    fun getUsers() : Call<ResponseGetUsersDTO>
}
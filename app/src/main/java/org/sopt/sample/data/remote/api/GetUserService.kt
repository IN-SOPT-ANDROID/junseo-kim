package org.sopt.sample.data.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GetUserService {
    @GET("api/users")
    fun getUsers(
        @Query("page")
        page: Int
    ): Call<ResponseGetUsersDTO>
}
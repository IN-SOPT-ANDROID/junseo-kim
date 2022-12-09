package org.sopt.sample.data.remote.api

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ImageService {
    @Multipart
    @POST("api/user/{userId}/image")
    fun uploadImage(
        @Path("userId") userId: Int,
        @Part("image") image: MultipartBody.Part
    ): Call<Unit>
}
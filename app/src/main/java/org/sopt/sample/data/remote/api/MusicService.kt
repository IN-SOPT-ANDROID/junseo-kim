package org.sopt.sample.data.remote.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.sopt.sample.data.remote.model.ResponseGetMusicDto
import retrofit2.Call
import retrofit2.http.*

interface MusicService {
    @GET("music/list")
    fun getMusicList(
    ): Call<ResponseGetMusicDto>

    @Multipart
    @POST("music")
    fun uploadMusic(
        @Part image: MultipartBody.Part,
        @PartMap request: HashMap<String, RequestBody>
    ): Call<Unit>
}
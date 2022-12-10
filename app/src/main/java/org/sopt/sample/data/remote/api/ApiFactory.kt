package org.sopt.sample.data.remote.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

object ApiFactory {
    private val client by lazy {
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            ).build()
    }

    val retrofitForAuthService: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://3.39.169.52:3000/")
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .client(client)
            .build()
    }

    inline fun <reified T> createAuthService(): T = retrofitForAuthService.create<T>(T::class.java)

    val retrofitForGetUserService: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://reqres.in/")
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .client(client)
            .build()
    }

    inline fun <reified T> createGetUserService(): T =
        retrofitForGetUserService.create<T>(T::class.java)

    val retrofitForImageService: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://3.39.169.52:3000/")
            .addConverterFactory(Json.asConverterFactory("multipart/form-data".toMediaType()))
            .client(client)
            .build()
    }

    inline fun <reified T> createImageService(): T =
        retrofitForImageService.create<T>(T::class.java)

    val retrofitForMusicService: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://3.34.53.11:8080/")
            .addConverterFactory(Json.asConverterFactory("multipart/form-data".toMediaType()))
            .client(client)
            .build()
    }

    inline fun <reified T> createMusicService(): T =
        retrofitForMusicService.create<T>(T::class.java)
}

object ServicePool {
    val authService = ApiFactory.createAuthService<AuthService>()
    val getUserService = ApiFactory.createGetUserService<GetUserService>()
    val imageService = ApiFactory.createImageService<ImageService>()
    val musicService = ApiFactory.createMusicService<MusicService>()
}
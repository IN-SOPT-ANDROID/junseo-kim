package org.sopt.sample.data.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import okhttp3.MediaType.Companion.toMediaType

object ApiFactory {
    val retrofit by lazy { Retrofit.Builder()
        .baseUrl("http://3.39.169.52:3000/")
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .build()}

    inline fun <reified T> create(): T = retrofit.create<T>(T::class.java)
}

object ServicePool{
    val authService = ApiFactory.create<AuthService>()
}
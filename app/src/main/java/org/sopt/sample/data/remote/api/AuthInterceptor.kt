package org.sopt.sample.data.remote.api

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val headerRequest = originalRequest.newBuilder()
            .header("token", "asdfasdf123123")
            .build()

        return chain.proceed(headerRequest)
    }

}
package com.weather.core.network.interceptor

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AddAppIdInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()

        val url: HttpUrl = request.url
            .newBuilder()
            .addQueryParameter("appid", "f9b0eb5802da77f37dfb171e8d2abe09")
            .build()

        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}
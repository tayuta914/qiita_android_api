package com.example.qiita_android_api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface QiitaApi {
    // エンドポイントを指定
    @GET("v2/items.json")
    fun items(): Call<List<Item>>
}

fun getApi(): QiitaApi {
    val baseApiUrl = "https://qiita.com/api/"
    val httpLogging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    val httpClientBuilder = OkHttpClient.Builder().addInterceptor(httpLogging)

    val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl(baseApiUrl)
        .client(httpClientBuilder.build())
        .build()

    return retrofit.create(QiitaApi::class.java)
}
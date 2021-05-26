package com.fruktorum.ftauth.network

import com.fruktorum.ftauth.BuildConfig
import com.fruktorum.ftauth.network.provider.AuthGlobalDataProvider
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


internal class RetrofitHelper(
    serverUrl: String
) {
    val authApi by lazy {
        getRetrofitInstance(serverUrl).create(AuthGlobalDataProvider::class.java)
    }


    private fun getRetrofitInstance(serverUrl: String): Retrofit {
        val httpClient = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(logging)
        }
        return Retrofit.Builder()
            .client(httpClient.build())
            .baseUrl(serverUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
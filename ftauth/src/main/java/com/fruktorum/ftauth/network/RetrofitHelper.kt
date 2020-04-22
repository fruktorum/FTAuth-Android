package com.fruktorum.ftauth.network

import com.fruktorum.ftauth.network.provider.AuthGlobalDataProvider
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
        return Retrofit.Builder()
            .baseUrl(serverUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
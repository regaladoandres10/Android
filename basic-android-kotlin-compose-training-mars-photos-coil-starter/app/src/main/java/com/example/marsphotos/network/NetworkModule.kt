package com.example.marsphotos.network

import AddCookiesInterceptor
import ReceivedCookiesInterceptor
import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModule {
    fun provideApi(context: Context): SICENETWService {

        val client = OkHttpClient.Builder()
            .addInterceptor(AddCookiesInterceptor(context))
            .addInterceptor(ReceivedCookiesInterceptor(context))
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://sicenet.surguanajuato.tecnm.mx/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(SICENETWService::class.java)
    }
}
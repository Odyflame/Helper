package com.example.myhelper

import android.util.Log
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitClientInstance {

    var retrofit: Retrofit?=null
    //BASE_URL은 ngrok에 따라 달라진다, 참고해야할사항

    val BASE_URL = "https://604c11eb.ngrok.io"
    //const val BASE_URL = "https://my-json-server.typicode.com/odyflame/testJson/MovieList"

    val httpclient : OkHttpClient.Builder = OkHttpClient.Builder()

    fun getRetrofitInstnace() :Retrofit {
        if (retrofit == null) {

            retrofit = retrofit2.Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        }

        Log.d("RetrofitClientInstance", retrofit.toString())

        return retrofit!!
    }

}
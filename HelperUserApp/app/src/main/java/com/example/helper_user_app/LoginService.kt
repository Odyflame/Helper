package com.example.myhelper

import android.provider.ContactsContract
import retrofit2.Call
import retrofit2.http.*

interface LoginService {

    @FormUrlEncoded
    @POST("login")
    fun loginService(
        @Field("email") email: String,
        @Field("password") password : String
    ) : Call<Login>


}
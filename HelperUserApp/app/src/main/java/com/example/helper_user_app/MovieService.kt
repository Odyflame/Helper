package com.example.myhelper

import com.example.helper_user_app.Movie
import com.example.helper_user_app.script
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface MovieService {

    var id:Int

    @GET("movies/{id}/")
    fun getMovieData(@Header("Authorization") auth: String, @Path("id") id: Int
    ) : Call<Movie>

    @GET("movies")
    fun getMoviesData(@Header("Authorization") auth: String): Call<List<Movie>>

    @GET("movies/{id}/scripts")
    fun getMovieScripts(@Header("Authorization") auth: String, @Path("id") id: Int ): Call<List<script>>

}
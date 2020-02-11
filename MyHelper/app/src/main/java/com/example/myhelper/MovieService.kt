package com.example.myhelper

import android.util.JsonToken
import com.google.gson.JsonObject
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*

interface MovieService {

    @GET("movie/:id/script")
    fun getMovieData(
        @Path("id") id : Int
    ) : Call<Movie>

    @GET("movies")
    fun getMoviesData( @Header("Authorization") auth : String) : Call<List<Movie>>

    @PUT("movies")
    fun postMovieStartTime( @Header("Authorization") auth : String, @Body movie : JsonObject) : Call<JsonObject>
}
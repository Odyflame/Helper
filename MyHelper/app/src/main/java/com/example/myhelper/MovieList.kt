package com.example.myhelper

import com.google.gson.annotations.SerializedName

class MovieList {
    @SerializedName("moviesList")
    var moviesList : List<Movie>?=null

    fun setMovieList(items :List<Movie>){
        this.moviesList = items
    }

    fun getMovieList() : List<Movie>{
        return moviesList!!
    }

    fun getSize() :Int?{
        return moviesList!!.size
    }

    fun getMovie(id :Int) :Movie{
        return moviesList!!.get(id)
    }
}
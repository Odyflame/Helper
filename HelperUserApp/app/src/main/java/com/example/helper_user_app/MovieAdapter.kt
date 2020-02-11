package com.example.helper_user_app

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MovieAdapter :RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    //영화 어뎁터
    var items : List<Movie>
    lateinit var context: Context

    val onMovieClickListener: MovieAdapter.OnMovieClickListener

    constructor(context: Context, movielist :List<Movie>, onMovieClickListener: OnMovieClickListener){
        this.items = movielist
        this.onMovieClickListener = onMovieClickListener
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapter.MovieViewHolder {
        context = parent.context

        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items!!.size
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onBindViewHolder(holder: MovieAdapter.MovieViewHolder, position: Int) {
        val item = items!!.get(position)

        holder.onBind(item)
        holder.mview.setOnClickListener {
            this.onMovieClickListener.onMovieClicked(item)
        }
    }

    inner class MovieViewHolder : RecyclerView.ViewHolder{

        var mview :View
        var movieTitle :TextView
        var movieGanre :TextView
        var movieImage :ImageView
        var movieSummary : TextView
        var drawable : GradientDrawable

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        constructor(movieview : View) : super(movieview){
            mview = movieview

            movieTitle = movieview.findViewById(R.id.Title)
            movieGanre = movieview.findViewById(R.id.Ganre)
            movieImage = movieview.findViewById(R.id.movieView)
            movieSummary = movieview.findViewById(R.id.description)

            drawable = context.getDrawable(R.drawable.radius) as GradientDrawable
        }


        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
        fun onBind(item : Movie){

            movieTitle.setText(item.title)
            movieGanre.setText(item.genre)
            movieSummary.setText(item.Summary)
            movieImage.background = drawable
            movieImage.clipToOutline = true

            //url사용할 때는 glide를 사용하는게 좋다
            Glide.with(mview).load(item.image).into(movieImage)
        }
    }

    interface OnMovieClickListener {
        fun onMovieClicked(movie: Movie)
    }
}
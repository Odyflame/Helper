package com.example.myhelper

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

    val onMovieClickListener : MovieAdapter.OnMovieClickListener

    constructor(context: Context, movielist :List<Movie>, onMovieClickListener: OnMovieClickListener){
        items = movielist
        this.onMovieClickListener = onMovieClickListener
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapter.MovieViewHolder {
        context = parent.context

        var layoutInflater = LayoutInflater.from(parent.context)
        var view = layoutInflater.inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items!!.size
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: MovieAdapter.MovieViewHolder, position: Int) {
        var item = items!!.get(position)

        holder.onBind(item)
        holder.mview.setOnClickListener {
            this.onMovieClickListener.onMovieClicked(item)
        }
    }

    fun setitems(items: List<Movie>) {
        this.items = items
    }

    inner class MovieViewHolder : RecyclerView.ViewHolder{

        var mview :View
        var movieTitle :TextView
        var movieGanre :TextView
        var movieImage :ImageView

        var movieSummary : TextView
        var drawble : GradientDrawable

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        constructor(movieview : View) : super(movieview){
            mview = movieview
            movieTitle = movieview.findViewById(R.id.Title)
            movieGanre = movieview.findViewById(R.id.Ganre)
            movieImage = movieview.findViewById(R.id.movieView)

            movieSummary = movieview.findViewById(R.id.description)
            drawble = context.getDrawable(R.drawable.radius) as GradientDrawable

           /* movieview.setOnClickListener( object : View.OnClickListener{
                override fun onClick(v: View) {
                    Toast.makeText(context, "recyclerview success!!!", Toast.LENGTH_SHORT).show()

                    var intent = Intent(v.context,  movie_details::class.java)

                    var textd = v.findViewById(R.id.Title) as TextView
                    var textd2 = v.findViewById(R.id.Ganre) as TextView
                    var textd3 = v.findViewById(R.id.Image) as TextView

                    intent.putExtra("title",  textd.text.toString())//제목
                    intent.putExtra("genre", textd2.text.toString())//장르
                    intent.putExtra("imagelink", textd3.text.toString())//이미지 링크

                    context.startActivity(intent)
                }
            })*/

        }


        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        fun onBind(item : Movie){
            movieTitle.setText(item.title)
            movieGanre.setText(item.genre)
            movieSummary.setText(item.Summary)
            movieImage.background = drawble
            movieImage.clipToOutline = true

            //앱에 설치된 이미지 그릴때 사용
            //movieImage.setImageResource(item.url!!.toInt())

            //url사용할 때는 glide를 사용하는게 좋다
            Glide.with(mview).load(item.image).into(movieImage)
        }
    }

    interface OnMovieClickListener {
        fun onMovieClicked(movie : Movie)
    }
}
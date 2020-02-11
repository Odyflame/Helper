package com.example.myhelper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_movie_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Thread.sleep


class MainActivity : AppCompatActivity(), MovieAdapter.OnMovieClickListener {

    lateinit var adapter : MovieAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var auth :String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var bundle = intent.extras

        auth = bundle!!.getString("auth") as String

        setContentView(R.layout.activity_main)

 /*       var pbar : ProgressBar = findViewById(R.id.progressBar)

        for(i in 0..100){
            pbar.setProgress(i)
        }*/

        //create handel for ther RetrofitInstance interface
        var serviece :MovieService = RetrofitClientInstance().getRetrofitInstnace().create(MovieService::class.java)

        //call the method with parameter in the interface to get the movie data
        var call : Call<List<Movie>> = serviece.getMoviesData(auth!!)
        Log.d("URL Called", call.request().url().toString())

        call.enqueue(object : Callback<List<Movie>> {
            //데이터를 받으면 onResponse
            override fun onResponse(call: Call<List<Movie>>, response: Response<List<Movie>>) {
                Log.d("onResponse", "YES!!")
                if(response.body()==null) {
                    Toast.makeText(this@MainActivity, "response went good..please try later!", Toast.LENGTH_SHORT).show()
                    Log.d("onResponse", "response. body is null")
                }else{
                    generateMovieList(response.body())
                }
            }

            //데이터를 받지 못하면 onFailure
            override fun onFailure(call: Call<List<Movie>>, t: Throwable?) {
                Log.d("onFailure", "no!!")

            }
        })
    }

    fun generateMovieList(movieList : List<Movie>) {

        recyclerView = findViewById(R.id.movieRecyclerView)
        adapter = MovieAdapter(this, movieList, this)
        var layoutmanager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutmanager
        recyclerView.adapter = adapter

    }

    override fun onMovieClicked(movie: Movie) {

        Toast.makeText(applicationContext, "this is intent to movieAdapter", Toast.LENGTH_SHORT).show()

        val intent = Intent(applicationContext,  movie_details::class.java)
        intent.putExtra("title",  movie.title)//제목
        intent.putExtra("genre", movie.genre)//장르
        intent.putExtra("imagelink", movie.image)//이미지 링크
        intent.putExtra("MovieID", movie.id.toString())
        intent.putExtra("StartTime", movie.StartTime)
        intent.putExtra("Auth", auth)

        startActivity(intent)

    }


}

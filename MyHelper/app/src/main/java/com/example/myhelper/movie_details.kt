package com.example.myhelper

import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_movie_details.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.POST
import java.io.IOException
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class movie_details : AppCompatActivity() {

    lateinit var tts : TextToSpeech
    override fun onBackPressed() {

        Toast.makeText(this, "back button pressed, a", Toast.LENGTH_SHORT).show()

        super.onBackPressed()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        //영화 상세 이미지 넣을 예정
        //액티비티 전환시 사용예정

        var bundle = intent.extras

        detail_title.text = bundle!!.getString("title")
        detail_ganre.text = bundle!!.getString("genre")

        var myid = bundle!!.getString("movieID")

        var movieiamge = findViewById(R.id.detail_movie) as ImageView

        Log.d("movie_details", bundle!!.getString("imagelink"))

        Glide.with(this).load(bundle!!.getString("imagelink")).into(movieiamge)

        var gradient = applicationContext.getDrawable(R.drawable.radius) as GradientDrawable

        var movieImage = findViewById(R.id.detail_movie) as ImageView
        //movieImage.background = gradient

         /*tts = TextToSpeech(this, object :TextToSpeech.OnInitListener{
            override fun onInit(status: Int) {
                if(status !=TextToSpeech.ERROR){
                    tts.setLanguage(Locale.KOREAN)
                }
            }
        })*/

        //tts.speak(detail_title.text.toString(), TextToSpeech.QUEUE_FLUSH, null)

        button.setOnClickListener {
            //sending movie start Button
            Log.d("OnClick", "mms")

            /*var mymovie :JsonObject = JsonObject()
            mymovie.addProperty("id", myid!!.toInt())
            mymovie.addProperty("title", detail_title.text.toString())
            mymovie.addProperty("genre", detail_ganre.text.toString())
            mymovie.addProperty("image", bundle!!.getString("imagelink"))
            mymovie.addProperty("StartTime", System.currentTimeMillis().toString())*/

           /* var movie = JsonObject()

            movie.addProperty("id", myid!!.toInt() + 1)
            movie.addProperty("title", detail_title.text.toString())
            movie.addProperty("genre", detail_ganre.text.toString())
            movie.addProperty("image", bundle!!.getString("imagelink"))
            movie.addProperty("StartTime", System.currentTimeMillis().toString())*/

            var movie = JsonObject()
            //movie.addProperty("id", myid!!.toInt() + 1)
            movie.addProperty("title", detail_title.text.toString())
            movie.addProperty("genre", detail_ganre.text.toString())
            movie.addProperty("image", bundle!!.getString("imagelink"))
            movie.addProperty("StartTime", (System.currentTimeMillis()).toString())

            //create handel for ther RetrofitInstance interface
            var serviece :MovieService = RetrofitClientInstance().getRetrofitInstnace().create(MovieService::class.java)

            var call = serviece.postMovieStartTime( bundle!!.getString("Auth").toString(), movie )

            call.enqueue(object : Callback<JsonObject> {
                override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {
                    Log.d("fuching onFailure", "nonnonno!!")
                }

                override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>?) {
                    try{
                        Log.e("response-success", response!!.body().toString())
                    }catch (e : Exception){
                        Log.d("fuching don't response", "nonnonno!!")
                    }

                }

            })

            Toast.makeText(this, "Send Movie StartTime succeess!! please anounce the customer to use our Helper-User-App!!", Toast.LENGTH_LONG).show()

            //call the method with parameter in the interface to get the movie data
            //var call : Call<List<Movie>> = serviece.getMoviesData(auth!!)

            //여기서 post

        }

        fun HttpPostData( movieid: String? ){

            try{
                //url 설정하고 접
                var url:URL = URL("https://4f4af8c3.ngrok.io")
                var http :HttpURLConnection = url.openConnection() as HttpURLConnection

                //전송 모드 - 기본적 설
                http.defaultUseCaches = (false)
                http.doInput = true
                http.doOutput = true
                http.requestMethod = "POST"

                http.setRequestProperty("content-type", "appliocation/x-www=form-urlencoded")


            }catch (e : IOException){
                //
            }

        }
    }
}

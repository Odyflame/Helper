package com.example.helper_user_app

import android.annotation.TargetApi
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.myhelper.MovieService
import com.example.myhelper.RetrofitClientInstance
import kotlinx.android.synthetic.main.activity_movie_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Build
import android.os.Handler
import android.speech.tts.TextToSpeech
import android.speech.tts.Voice
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class movie_details : AppCompatActivity() {

    var script_Array: List<script>? = null
    var drawingScriptList: ArrayList<script> = ArrayList()

    val handler = Handler()
    var movieStartTime = 1575006666000
    //1575283302676

    lateinit var tts: TextToSpeech

    override fun onDestroy() {
        super.onDestroy()
        // TTS 객체가 남아있다면 실행을 중지하고 메모리에서 제거한다.
        if (tts != null) {
            tts.stop()
            tts.shutdown()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        //영화 상세 이미지 넣을 예정
        //액티비티 전환시 사용예정

        tts = TextToSpeech(applicationContext, object : TextToSpeech.OnInitListener {
            override fun onInit(status: Int) {
                if (status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.KOREAN)
                }
            }

        })

        var bundle = intent.extras

        detail_title.setText(bundle!!.getString("title"))

        var myid = bundle!!.getString("MovieID")

        var auth = bundle!!.getString("Auth")

        var movieiamge = findViewById(R.id.detail_movie) as ImageView

        Log.d("movie_details", bundle!!.getString("imagelink"))

        Glide.with(this).load(bundle!!.getString("imagelink")).into(movieiamge)

        var format: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
        var date = bundle!!.getString("StartTime")!!

        //var mydate = format.parse(date)
        //movieStartTime = mydate.time

        movieStartTime = date.toLong()


        val serviece: MovieService =
            RetrofitClientInstance().getRetrofitInstnace().create(MovieService::class.java)
        val call: Call<List<script>> = serviece.getMovieScripts(auth!!, myid!!.toInt())
        Log.d("URL Called", call.request().url().toString())

        call.enqueue(object : Callback<List<script>> {
            override fun onFailure(call: Call<List<script>>, t: Throwable?) {
                Log.d("onFailure", "no!!")
            }

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onResponse(call: Call<List<script>>, response: Response<List<script>>) {
                Log.d("onResponse", "YES!!")
                if (response.body() == null) {
                    Toast.makeText(
                        this@movie_details,
                        "response went good..please try later!",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d("onResponse", "response. body is null")
                } else {

                    //여기서 구현을 다시 해보자
                    //자막 데이터를 받아와야 한다.
                    //자막 데이터를 어디에 저장할 것인가?
                    //script_Array에 저장

                    getMovieScript(response.body())
                    scriptCheck()
                }
            }
            //데이터를 받으면 onresponse

        })


        button.setOnClickListener {
            Log.d("OnClick", "mms")
            /* var thread : BackgroundScriptsThread = BackgroundScriptsThread()
             thread.start()*/
            val call: Call<Movie> = serviece.getMovieData(auth!!, myid!!.toInt())
            Log.d("URL Called", call.request().url().toString())

            call.enqueue(object : Callback<Movie> {
                override fun onFailure(call: Call<Movie>, t: Throwable?) {
                    Log.d("onFailure", "no!!")
                }

                override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                    Log.d("onResponse", "YES!!")
                    if (response.body() == null) {
                        Toast.makeText(
                            this@movie_details,
                            "response went good..please try later!",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.d("onResponse", "response. body is null")
                    } else {
                        movieStartTime = response.body().StartTime!!.toLong()
                    }
                }
                //데이터를 받으면 onresponse

            })
        }

        //핸들러가 초단위인지 밀리세컨즈단위인지 물어보자
        //이시발게 중간에서 실행된다 원래는 됐었는
    }

    inner class BackgroundScriptsThread : Thread() {
        var value: Int = 0

        lateinit var mylinear: LinearLayout
        override fun run() {

            if (script_Array != null) {
                val current = System.currentTimeMillis()

                script_Array!!.forEach {
                    if (it.begin_at != null && it.begin_at!! + movieStartTime <= current) {
                        drawingScriptList.add(it)
                        val scriptView = TextView(this@movie_details)
                        scriptView.text = it.script
                        detail_script.addView(scriptView)
                    }
                }
                drawingScriptList.forEach {
                    if (it.end_at != null && it.end_at!! < current - movieStartTime) {
                        val index = drawingScriptList.indexOf(it)
                        detail_script.removeViewAt(index)
                        drawingScriptList.removeAt(index)
                    }
                }
            }

            handler.post(object : Runnable {
                override fun run() {

                }

            })

        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun scriptCheck() {
        if (script_Array != null) {

            val current = System.currentTimeMillis()

            script_Array!!.forEach {
                if (it.begin_at != null && current - movieStartTime >= it.begin_at!!) {
                    if (drawingScriptList.contains(it) == false) {

                        this.drawingScriptList.add(it)//리스트는 foreach문을 사용하여 iterator를 사용할 수 있다.
                        val scriptView = TextView(this)
                        scriptView.text = it.script
                        scriptView.textSize = 30F
                        this.detail_script.addView(scriptView)
                        this.detail_script.postInvalidate()

/*
                        var voice =  Voice("man", Locale.KOREAN, Voice.QUALITY_HIGH, Voice.LATENCY_LOW, false, null)
                        tts.setVoice(voice)
*/

                       /* tts.setPitch(0.1f)
                        tts.setSpeechRate(1.0f)
                        tts.speak(it.script, TextToSpeech.QUEUE_FLUSH, null)
*/


                    }

                }
            }

            val iterator1 = drawingScriptList.iterator()

            while (iterator1.hasNext()) {
                var tempscirpt = iterator1.next()

                if (tempscirpt.end_at != null && tempscirpt.end_at!! < current - movieStartTime) {
                    val index = drawingScriptList.indexOf(tempscirpt)
                    this.detail_script.removeViewAt(index)
                    iterator1.remove()
                    this.detail_script.postInvalidate()
                    //tts.shutdown()
                }

            }

        }
        this.handler.post {
            scriptCheck()
        }
    }

    override fun onBackPressed() {
        this.handler.removeCallbacksAndMessages(null)
        this.tts.stop()
        this.tts.shutdown()

        super.onBackPressed()
    }

    fun showScript(script: String) {
        // TODO : 화면에 자막 그리기
        // TODO : fuckfin bitch man


    }

    fun getMovieScript(scriptList: List<script>) {
        if (script_Array != null) {
            script_Array = null
        }
        script_Array = scriptList
    }

}

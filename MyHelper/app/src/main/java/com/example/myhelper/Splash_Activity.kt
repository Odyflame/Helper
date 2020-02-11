package com.example.myhelper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Splash_Activity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_)

        var sub = findViewById(R.id.sub) as Button
        var frombottom = AnimationUtils.loadAnimation(this, R.anim.frombottom)

        var ballon = findViewById(R.id.ballon) as ImageView

        var fromtop = AnimationUtils.loadAnimation(this, R.anim.fromup)

        sub.animation = frombottom
        ballon.animation = fromtop

        var mytextview1 = findViewById(R.id.ID) as EditText
        var mytextview2 = findViewById(R.id.Password) as EditText

        mytextview1.animation = fromtop
        mytextview2.animation = frombottom

        sub.setOnClickListener {

            if(mytextview1.text==null || mytextview2.text==null){
                Toast.makeText(applicationContext, "put id and pw!!", Toast.LENGTH_SHORT).show()
            }else{
                var mylogin : LoginService = RetrofitClientInstance().getRetrofitInstnace().create(LoginService::class.java)

                var call1 : Call<Login> = mylogin.loginService(mytextview1.text.toString(), mytextview2.text.toString())
                Log.d("login call", call1.request().url().toString())


                call1.enqueue(object :Callback<Login>{
                    override fun onFailure(call: Call<Login>?, t: Throwable?) {
                        Log.d("fucking bitch", t.toString())
                    }

                    override fun onResponse(call: Call<Login>?, response: Response<Login>?) {

                        if(response?.body()?.email==null || response?.body()?.authToken ==null){
                            //이 중 받아오지 못했을 경우
                            Toast.makeText(applicationContext, "please input right id and pw", Toast.LENGTH_SHORT).show()
                        }else{
                            Log.d("success", response?.body()?.email)
                            Log.d("success", response?.body()?.authToken)

                            val intent = Intent(this@Splash_Activity, MainActivity::class.java)

                            intent.putExtra("auth", response?.body()?.authToken)

                            startActivity(intent)
                        }

                    }
                })

            }
        }


    }
}

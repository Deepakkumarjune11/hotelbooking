package com.my.mobileapplicationtask

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity()
{
    private lateinit var sharedPreferenceManager: SharedPreferenceManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val clickMeButton=findViewById<Button>(R.id.registerButton)
        val loginButton =findViewById<TextView>(R.id.loginButton)

        clickMeButton.setOnClickListener {
            val loading = LoadingDialog(this)
            loading.startLoading()
            val handler = Handler()
            handler.postDelayed(object : Runnable {
                override fun run() {
                    loading.isDismiss()
                }
            }, 3000)
            val regScreenIntent = Intent(this@LoginActivity,RegisterActivity::class.java)

            startActivity(regScreenIntent)



        }
        val intent = Intent(this, HotelActivity::class.java)
        val profileIntent=Intent(this,profileActivity::class.java)
        sharedPreferenceManager = SharedPreferenceManager(this,)
        loginButton.setOnClickListener {

            val email = findViewById<TextInputLayout>(R.id.email).editText?.text
            val password = findViewById<TextInputLayout>(R.id.password).editText?.text

            val user = UserData(email.toString(), password.toString())


            CoroutineScope(Dispatchers.IO).launch {
                val sampleApplication=application as HotelApplication
                val service=sampleApplication.loginService
                service.postData(user).enqueue(object : Callback<loginData?> {
                    override fun onResponse(call: Call<loginData?>, response: Response<loginData?>) {
                        if(response.isSuccessful)
                        {
                            val intent = Intent(this@LoginActivity,HotelActivity::class.java)
                            sharedPreferenceManager.saveAuthToken(response.body()?.token)
                            sharedPreferenceManager.saveEmail(response.body()?.email)
                            sharedPreferenceManager.saveMember(response.body()!!.memberSince)
                            intent.putExtra("string",response.body()?.token)
                            profileIntent.putExtra("string",response.body()?.email)
                            startActivity(intent)
                            Toast.makeText(this@LoginActivity, "Login success!", Toast.LENGTH_SHORT)
                                .show()


                        }
                        else
                        {
                            Toast.makeText(this@LoginActivity, "Login failed!", Toast.LENGTH_SHORT)
                                .show()

                        }
                    }

                    override fun onFailure(call: Call<loginData?>, t: Throwable) {

                    }
                })
            }
        }


    }


}
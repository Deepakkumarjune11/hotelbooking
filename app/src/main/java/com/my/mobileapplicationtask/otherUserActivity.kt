package com.my.mobileapplicationtask

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class otherUserActivity : AppCompatActivity() {
    private lateinit var sharedPreference: SharedPreferenceManager

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_user)
        val apiclient = application as HotelApplication
        sharedPreference = SharedPreferenceManager(this)
        var intent = Intent(this@otherUserActivity, profileActivity::class.java)
        var token = sharedPreference.fetchAuthToken()
        val items: MutableList<otherUserDataClass> = mutableListOf<otherUserDataClass>()
        if (sharedPreference.fetchAuthToken() != null) {
            CoroutineScope(Dispatchers.IO).launch {
                val result = apiclient.otherUserService.otherUserDetails("Bearer " + token)
                var i = 0
                if (result.isSuccessful) {
                    while (i < result.body()?.users!!.size) {
                        items.add(result.body()?.users!![i])
                        i += 1
                    }
                } else {
                    startActivity(intent)
                }
                withContext(Dispatchers.Main) {
                    val recycle = findViewById<RecyclerView>(R.id.otherUserRecylcer)
                    recycle.adapter = OtherUserAdapterClass(items, this@otherUserActivity)
                    recycle.layoutManager = LinearLayoutManager(this@otherUserActivity)
                }

            }
        } else {
            Toast.makeText(
                this@otherUserActivity,
                "Not Available!!!",
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }
}
package com.my.mobileapplicationtask

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class LoginHistoryActivity : AppCompatActivity() {
    private lateinit var sharedPreference: SharedPreferenceManager
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_history)
        val memberdate=findViewById<TextView>(R.id.MemberDate)
        sharedPreference = SharedPreferenceManager(this)
        var member=sharedPreference.fetchMember()

        val sdf = SimpleDateFormat("dd-MMM-YYYY")
        val netDate = Date(member)
        val displayThisDate = sdf.format(netDate)
        memberdate.text="$displayThisDate"



        val apiclient = application as HotelApplication
        sharedPreference = SharedPreferenceManager(this)
        var intent= Intent(this@LoginHistoryActivity,profileActivity::class.java)

        var token = sharedPreference.fetchAuthToken()
        val items: MutableList<LoginHistoryDataClass> = mutableListOf<LoginHistoryDataClass>()
        if (sharedPreference.fetchAuthToken() != null) {
            CoroutineScope(Dispatchers.IO).launch {
                val result = apiclient.historyService.logDetails("Bearer " + token)
                var i = 0
                if (result.isSuccessful) {
                    while (i < result.body()?.loginEntries!!.size) {
                        items.add(result.body()?.loginEntries!![i])
                        i += 1
                    }
                } else {
                    startActivity(intent)
                }
                withContext(Dispatchers.Main) {
                    val recycle = findViewById<RecyclerView>(R.id.loginHistoryRecycler)
                    recycle.adapter = loginHistoryAdapterClass(items,this@LoginHistoryActivity)
                    recycle.layoutManager = LinearLayoutManager(this@LoginHistoryActivity)
                }

            }
        } else {
            Toast.makeText(
                this@LoginHistoryActivity,
                "Not Available!!!",
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }
    }

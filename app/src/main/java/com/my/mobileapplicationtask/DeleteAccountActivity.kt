package com.my.mobileapplicationtask

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeleteAccountActivity : AppCompatActivity() {
    private lateinit var sharedPreference: SharedPreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_account)
        sharedPreference = SharedPreferenceManager(this)
        findViewById<TextView>(R.id.deleteAccountEmail).text = "${sharedPreference.fetchEmail()}"

        findViewById<Button>(R.id.accountDeleteButton).setOnClickListener {

            CoroutineScope(Dispatchers.IO).launch {
                val sampleApplication = application as HotelApplication
                val service = sampleApplication.delete
                service.deleteUser("Bearer ${sharedPreference.fetchAuthToken()}").enqueue(object :
                    Callback<Void?> {
                    override fun onResponse(call: Call<Void?>, response: Response<Void?>) {

                        if (response.isSuccessful) {
                            Toast.makeText(
                                applicationContext,
                                "Successfully deleted",
                                Toast.LENGTH_LONG
                            ).show()
                            val preferences = getSharedPreferences("prefs", Context.MODE_PRIVATE)
                            val editor = preferences.edit()
                            editor.clear()
                            editor.apply()
                            finish()
                            val intent =
                                Intent(this@DeleteAccountActivity, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {

                        }
                    }

                    override fun onFailure(call: Call<Void?>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()

                    }
                })
            }

        }
    }}
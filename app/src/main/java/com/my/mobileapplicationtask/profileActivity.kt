package com.my.mobileapplicationtask

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import java.util.*

class profileActivity : AppCompatActivity() {
    private lateinit var sharedPreference: SharedPreferenceManager
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val image=findViewById<ImageView>(R.id.profilePictureView)
        val profileEmail=findViewById<TextView>(R.id.profileEmailText)
        val emailEditButton=findViewById<Button>(R.id.emailEditButton)
        val membershipText=findViewById<TextView>(R.id.membershipText)
        val loginHistoryButton=findViewById<Button>(R.id.loginHistoryButton)
        val deleteAccountButton=findViewById<Button>(R.id.deleteAccountButton)
        val logoutButton=findViewById<Button>(R.id.logoutButton)

        sharedPreference = SharedPreferenceManager(this)
        var email = sharedPreference.fetchEmail()
        profileEmail.text="$email"
        var member=sharedPreference.fetchMember()

        val sdf = SimpleDateFormat("dd-MMM-YYYY")
        val netDate = Date(member)
        val displayThisDate = sdf.format(netDate)
        membershipText.text="              $displayThisDate"

        var changeEmailIntent= Intent(this@profileActivity,ChangeEmailActivity::class.java)
        emailEditButton.setOnClickListener{
            startActivity(changeEmailIntent)
        }
        loginHistoryButton.setOnClickListener{
            var LoginHistoryIntent= Intent(this@profileActivity,LoginHistoryActivity::class.java)
            startActivity(LoginHistoryIntent)
        }
        deleteAccountButton.setOnClickListener{
            var deleteIntent= Intent(this@profileActivity,DeleteAccountActivity::class.java)
            startActivity(deleteIntent)
        }


        logoutButton.setOnClickListener(){
            var logoutIntent= Intent(this@profileActivity,LoginActivity::class.java)
            startActivity(logoutIntent)
        }
    }
}
package com.my.mobileapplicationtask

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.my.mobileapplicationtask.databinding.ActivityHotelBinding
import kotlinx.android.synthetic.main.activity_hotel.*
import kotlinx.android.synthetic.main.drawermenu.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HotelActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHotelBinding
    private lateinit var sharedPreference: SharedPreferenceManager
    lateinit var toggle:ActionBarDrawerToggle
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item))
        {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotel)

        var profileIntent= Intent(this@HotelActivity,profileActivity::class.java)
        var myReserveIntent= Intent(this@HotelActivity,MyReservationsActivity::class.java)
        var otherUserIntent= Intent(this@HotelActivity,otherUserActivity::class.java)
        val navview=findViewById<NavigationView>(R.id.navView)
        val drawerlay=findViewById<DrawerLayout>(R.id.drawerLayout)
        val drawerEmailId = navview.getHeaderView(0).findViewById<TextView>(R.id.emailDrawerText)
        sharedPreference = SharedPreferenceManager(this)
        drawerEmailId.text = "${sharedPreference.fetchEmail()}"


        toggle= ActionBarDrawerToggle(this,drawerlay,toolbar,R.string.open,R.string.close)

        drawerlay.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.icon_image)
        navview.setNavigationItemSelectedListener{
            when(it.itemId)
            {
                R.id.profileId-> {startActivity(profileIntent)}
                R.id.myreservationsList -> {startActivity(myReserveIntent)}
                R.id.otherUserList -> {startActivity(otherUserIntent)}
            }
            true
        }


        val string = intent.getStringExtra("string")
        val apiclient = application as HotelApplication
        sharedPreference = SharedPreferenceManager(this)
        var intent = Intent(this, LoginActivity::class.java)

        var token = sharedPreference.fetchAuthToken()
        val items: MutableList<HotelData> = mutableListOf<HotelData>()
        if (sharedPreference.fetchAuthToken() != null) {
            CoroutineScope(Dispatchers.IO).launch {
                val result = apiclient.hotelService.GetHotels("Bearer " + token)
                var i = 0
                if (result.isSuccessful) {
                    while (i < result.body()?.hotels!!.size) {
                        items.add(result.body()?.hotels!![i])
                        i += 1
                    }
                } else {
                    startActivity(intent)
                }
                withContext(Dispatchers.Main) {
                    val recycle = findViewById<RecyclerView>(R.id.recycle)
                    recycle.adapter = AdapterClass(items,this@HotelActivity)
                    recycle.layoutManager = LinearLayoutManager(this@HotelActivity)
                }

            }
        } else {
            startActivity(intent)
        }
        supportActionBar?.hide()
        setSupportActionBar(toolbar)
    }
}
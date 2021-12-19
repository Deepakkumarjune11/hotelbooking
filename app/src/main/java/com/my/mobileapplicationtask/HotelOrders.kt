package com.my.mobileapplicationtask

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HotelOrders : AppCompatActivity() {
    lateinit var sharedPreference:SharedPreferenceManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotel_orders)
        val img=findViewById<ImageView>(R.id.images)
        val name=findViewById<TextView>(R.id.name)
        val rooms=findViewById<TextView>(R.id.roomsLeft)
        val price=findViewById<TextView>(R.id.pricePerNight)
        val create=findViewById<Button>(R.id.Createreservation)
        val reservedby=findViewById<TextView>(R.id.alreadyreserved)
        var text=intent.getIntExtra("ID",0)
        sharedPreference= SharedPreferenceManager(this)
        var token=sharedPreference.fetchAuthToken()
        var apiClient=application as HotelApplication
        CoroutineScope(Dispatchers.IO).launch {
            val result=apiClient.hotelService.GetHotels("Bearer "+ token)
            var i=0
            var res2:HotelData?=null
            if(result.isSuccessful){
                while(i<result.body()?.hotels!!.size){
                    if(result.body()?.hotels!![i].id==text){
                        res2=result.body()?.hotels!![i]
                        break
                    }
                    i+=1
                }
            }
            else{

            }
            withContext(Dispatchers.Main){
                Picasso.get().load(res2?.url).into(img);
                name.text=res2?.name
                rooms.text = "Rooms Left : ${res2?.roomsLeft}"
                price.text = "Price per Room : ${res2?.pricePerNight}$"
                reservedby.text = "Already reserved by ${res2?.reservedByPeople} people"
            }
        }
        create.setOnClickListener{
            var intent= Intent(this,ConfirmHotels::class.java)
            intent.putExtra("Id",text)
            startActivity(intent)
        }
        //Toast.makeText(this,"$text",Toast.LENGTH_SHORT).show()
    }
}
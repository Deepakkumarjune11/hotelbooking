package com.my.mobileapplicationtask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ConfirmHotels : AppCompatActivity() {
    lateinit var sharedPreference:SharedPreferenceManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_hotels)
        val img=findViewById<ImageView>(R.id.image)
        val qty=findViewById<TextView>(R.id.qty)
        val add=findViewById<Button>(R.id.add)
        val sub=findViewById<Button>(R.id.reduce)
        val pricce=findViewById<TextView>(R.id.itemprice)
        val create=findViewById<Button>(R.id.reserveBtn)
        var text=intent.getIntExtra("Id",0)
        var prices:Int?=0
        var count=0
        sharedPreference= SharedPreferenceManager(this)
        var token=sharedPreference.fetchAuthToken()
        var apiClient=application as HotelApplication
        CoroutineScope(Dispatchers.IO).launch {
            val result=apiClient.hotelService.GetHotels("Bearer "+token)
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
                Picasso.get().load(res2?.url).into(img)
                prices=res2?.pricePerNight?.toInt()
            }
        }
        add.setOnClickListener{
            count+=1
            qty.text="$count"
            val pp=prices!!.toInt()
            pricce.text="$ ${pp*count}"
        }
        sub.setOnClickListener{
            if(count>0){
                count-=1
            }
            else
                count=0
            qty.text="$count"
            val pp=prices!!.toInt()
            pricce.text="$ ${pp*count}"
        }
        create.setOnClickListener{
            if(count>0){
                CoroutineScope(Dispatchers.IO).launch {

                    var result=apiClient.hotelService.PlaceHotelReservations("Bearer "+token, Hotels(text,count))
                    withContext(Dispatchers.Main){
                        if(result!!.isSuccessful){
                            Toast.makeText(this@ConfirmHotels,"Stock Purchased successful", Toast.LENGTH_SHORT).show()
                        }
                    }

                }
            }
            else{
                Toast.makeText(this@ConfirmHotels,"Please select number of stock items", Toast.LENGTH_SHORT).show()
            }
        }

    }
}
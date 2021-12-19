package com.my.mobileapplicationtask

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class AdapterClass(var hotelsVal:MutableList<HotelData>, var context: Context): RecyclerView.Adapter<AdapterClass.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view=inflater.inflate(R.layout.items,parent,false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text=hotelsVal[position].name
        holder.room.text="Rooms Left : ${hotelsVal[position].roomsLeft}"
        holder.price.text="${hotelsVal[position].pricePerNight}$ per Night"
        Picasso.get().load(hotelsVal[position].url).into(holder.image);
    }

    override fun getItemCount(): Int {
        return hotelsVal.size
    }
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val image=itemView.findViewById<ImageView>(R.id.hotelimage)
        val name=itemView.findViewById<TextView>(R.id.name)
        val room=itemView.findViewById<TextView>(R.id.room)
        val price=itemView.findViewById<TextView>(R.id.amount)

        var id:Int=0

        init{
            itemView.setOnClickListener{
                id=adapterPosition
                val intent= Intent(context,HotelOrders::class.java)
                intent.putExtra("ID",hotelsVal[id].id)
                ContextCompat.startActivity(context, intent, Bundle())
            }
        }

    }
}
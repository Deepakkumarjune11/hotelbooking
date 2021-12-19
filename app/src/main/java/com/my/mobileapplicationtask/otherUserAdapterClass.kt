package com.my.mobileapplicationtask

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class OtherUserAdapterClass(var UsersVal:MutableList<otherUserDataClass>, var context: Context):
    RecyclerView.Adapter<OtherUserAdapterClass.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OtherUserAdapterClass.ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.otheruserlist, parent, false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return UsersVal.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val otherUserEmail = itemView.findViewById<TextView>(R.id.otheruserEmail)
        val ownsStock = itemView.findViewById<TextView>(R.id.ownsStockText)


    }

    override fun onBindViewHolder(holder: OtherUserAdapterClass.ViewHolder, position: Int) {
        holder.otherUserEmail.text = "${UsersVal[position].email}"
        holder.ownsStock.text = "Owns stocks: ${UsersVal[position].ownsStocks}"

    }
}
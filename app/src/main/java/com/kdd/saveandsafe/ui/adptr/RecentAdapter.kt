package com.kdd.saveandsafe.ui.adptr

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kdd.saveandsafe.R
import com.kdd.saveandsafe.dtbse.ety.ItemEntity

class RecentAdapter(val recent_list : List<ItemEntity>) : RecyclerView.Adapter<RecentAdapter.RecentViewHolder>() {

    class RecentViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val name : TextView = itemView.findViewById(R.id.cv_tv_item_name)
        val price : TextView = itemView.findViewById(R.id.cv_tv_item_price)
        val time : TextView = itemView.findViewById(R.id.cv_tv_item_time)
        val date : TextView = itemView.findViewById(R.id.cv_tv_item_date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.rv_recent, parent, false)
        return RecentViewHolder(view)
    }

    override fun getItemCount(): Int = recent_list.size

    override fun onBindViewHolder(holder: RecentViewHolder, position: Int) {
        val i_name = recent_list[position]
        val i_price = recent_list[position]
        val i_time = recent_list[position]
        val i_date = recent_list[position]

        holder.name.text = i_name.i_name
        holder.price.text = i_price.i_price.toString()
        holder.time.text = i_time.i_time
        holder.date.text = i_date.i_date.toString()
    }
}
package com.kdd.saveandsafe.ui.adptr

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kdd.saveandsafe.R
import com.kdd.saveandsafe.dtbse.ety.PriceEntity

class HistoryAdapter(val prices_list : List<PriceEntity>) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    class HistoryViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val amount : TextView = itemView.findViewById(R.id.cv_tv_amount)
        val date : TextView = itemView.findViewById(R.id.cv_tv_date)
        val updated_amount : TextView = itemView.findViewById(R.id.cv_tv_update_amount)
        val total_items : TextView = itemView.findViewById(R.id.cv_tv_total_items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val mView = layoutInflater.inflate(R.layout.rv_history, parent, false)
        return HistoryViewHolder(mView)
    }

    override fun getItemCount(): Int = prices_list.size

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val amount = prices_list[position]
        val date = prices_list[position]
        val updated_amount = prices_list[position]
        val total_items = prices_list[position]
        holder.amount.text = amount.p_amount.toString()
        holder.date.text = date.p_date.toString()
        holder.updated_amount.text = updated_amount.p_updated_amount.toString()
        holder.total_items.text = total_items.p_total_items.toString()
    }
}
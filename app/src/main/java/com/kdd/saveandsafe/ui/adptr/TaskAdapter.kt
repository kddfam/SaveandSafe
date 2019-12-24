package com.kdd.saveandsafe.ui.adptr

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kdd.saveandsafe.R
import com.kdd.saveandsafe.dtbse.ety.TaskEntity

class TaskAdapter(val task_list : List<TaskEntity>) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val t_name : TextView = itemView.findViewById(R.id.cv_tv_task_name)
        val t_etime : TextView = itemView.findViewById(R.id.cv_tv_task_etime)
        val t_date : TextView = itemView.findViewById(R.id.cv_tv_task_date)
        val t_time : TextView = itemView.findViewById(R.id.cv_tv_task_time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.rv_tasks, parent, false)
        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int = task_list.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val name = task_list[position]
        val etime = task_list[position]
        val date = task_list[position]
        val time = task_list[position]

        holder.t_name.text = name.t_name.toString()
        holder.t_etime.text = etime.t_e_time.toString()
        holder.t_date.text = date.t_date.toString()
        holder.t_time.text = time.t_time.toString()
    }
}
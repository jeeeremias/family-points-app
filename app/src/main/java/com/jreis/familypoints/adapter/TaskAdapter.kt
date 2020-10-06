package com.jreis.familypoints.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jreis.familypoints.dto.Task
import com.jreis.familypoints.holder.TaskViewHolder

class TaskAdapter(private val tasks: ArrayList<Task>) : RecyclerView.Adapter<TaskViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return TaskViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.bind(task)
    }

    fun addItem(task: Task, index: Int) {
        tasks.add(index, task)
        notifyItemInserted(index)
    }
}
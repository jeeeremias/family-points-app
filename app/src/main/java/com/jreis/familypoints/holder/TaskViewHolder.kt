package com.jreis.familypoints.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jreis.familypoints.R
import com.jreis.familypoints.dto.Task

class TaskViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.routine_list_item, parent, false)) {

    fun bind(task: Task) {

    }
}
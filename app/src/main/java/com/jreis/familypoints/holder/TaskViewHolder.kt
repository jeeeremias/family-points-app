package com.jreis.familypoints.holder

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import com.jreis.familypoints.R
import com.jreis.familypoints.dto.Task

class TaskViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.routine_item, parent, false)) {

    private val nameTextView: TextView = itemView.findViewById(R.id.task_name)
    private val timeTextView: TextView = itemView.findViewById(R.id.task_time)
    private val assigneeTextView: TextView = itemView.findViewById(R.id.task_assignee)
    private val image: ImageView = itemView.findViewById(R.id.task_item_image)
    private val storage = FirebaseStorage.getInstance()

    fun bind(task: Task) {
        nameTextView.text = task.name
        timeTextView.text = task.time
        assigneeTextView.text = task.assignee
        val oneMegabyte: Long = 1024 * 1024
        storage.getReference("task_icons/" + task.icon).getBytes(oneMegabyte).addOnSuccessListener {
            val img = BitmapFactory.decodeByteArray(it, 0, it.size)
            image.setImageBitmap(img)
        }.addOnFailureListener {
            image.setImageResource(R.drawable.task)
        }
    }
}
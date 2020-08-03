package com.jreis.familypoints.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jreis.familypoints.OnItemClickListener
import com.jreis.familypoints.dto.User
import com.jreis.familypoints.holder.UsersRoutineViewHolder

class UsersRoutineAdapter(private val users: List<User>, private val onItemClickListener: OnItemClickListener<User>) : RecyclerView.Adapter<UsersRoutineViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersRoutineViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return UsersRoutineViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: UsersRoutineViewHolder, position: Int) {
        val user: User = users[position]
        holder.bind(user, onItemClickListener)
    }
}
package com.jreis.familypoints.holder

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import com.jreis.familypoints.OnItemClickListener
import com.jreis.familypoints.R
import com.jreis.familypoints.dto.User

class UsersRoutineViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.users_routine_item, parent, false)), View.OnClickListener {

    private val nameTextView: TextView = itemView.findViewById(R.id.user_routine_name)
    private val image: ImageView = itemView.findViewById(R.id.user_routine_avatar)
    private lateinit var onItemClickListener: OnItemClickListener<User>
    private val storage = FirebaseStorage.getInstance()

    fun bind(user: User, onItemClickListener: OnItemClickListener<User>) {
        nameTextView.text = user.firstName
        val oneMegabyte: Long = 1024 * 1024
        storage.getReference("profile_pictures/" + user.profilePic).getBytes(oneMegabyte).addOnSuccessListener {
            val img = BitmapFactory.decodeByteArray(it, 0, it.size)
            image.setImageBitmap(img)
        }.addOnFailureListener {
            image.setImageResource(R.drawable.img_bravo)
        }
        itemView.setOnClickListener(this)
        this.onItemClickListener = onItemClickListener
    }

    override fun onClick(v: View?) {
        onItemClickListener.onItemClick(adapterPosition)
    }
}
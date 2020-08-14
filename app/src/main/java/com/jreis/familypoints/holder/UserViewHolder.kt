package com.jreis.familypoints.holder

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import com.jreis.familypoints.R
import com.jreis.familypoints.dto.User

class UserViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item_with_image, parent, false)) {

    private val nameTextView: TextView = itemView.findViewById(R.id.text_item_name)
    private val kinshipTextView: TextView = itemView.findViewById(R.id.text_item_kinship)
    private val image: ImageView = itemView.findViewById(R.id.image_item_user)
    private val storage = FirebaseStorage.getInstance()

    fun bind(user: User) {
        nameTextView.text = user.firstName
        kinshipTextView.text = user.kinship
        val oneMegabyte: Long = 1024 * 1024
        storage.getReference("profile_pictures/" + user.profilePic).getBytes(oneMegabyte).addOnSuccessListener {
            val img = BitmapFactory.decodeByteArray(it, 0, it.size)
            image.setImageBitmap(img)
        }.addOnFailureListener {
            image.setImageResource(R.drawable.img_bravo)
        }
    }
}
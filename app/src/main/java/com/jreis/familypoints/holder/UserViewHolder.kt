package com.jreis.familypoints.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jreis.familypoints.R
import com.jreis.familypoints.dto.User

class UserViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item_with_image, parent, false)) {

    private var nameTextView: TextView = itemView.findViewById(R.id.text_item_name)
    private var kinshipTextView: TextView = itemView.findViewById(R.id.text_item_kinship)
    private var image: ImageView = itemView.findViewById(R.id.image_item_user)

    fun bind(user: User) {
        nameTextView.text = user.firstName
        kinshipTextView.text = user.kinship
        image.setImageResource(R.drawable.ic_person)
    }
}
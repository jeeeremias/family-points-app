package com.jreis.familypoints

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.jreis.familypoints.adapter.UserAdapter
import com.jreis.familypoints.dto.User
import kotlinx.android.synthetic.main.fragment_participants.*

class ParticipantsFragment : Fragment() {
    private val usersDatabase = FirebaseDatabase.getInstance().getReference("users")
    private val users = ArrayList<User>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_participants, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        usersDatabase.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(usersSnapshot: DataSnapshot) {
                for (userSnapshot in usersSnapshot.children) {
                    userSnapshot.getValue(User::class.java)?.let { users.add(it) }
                }
                recyclerView_participants.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = UserAdapter(users)
                }
            }

        })
    }
}

package com.jreis.familypoints

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.jreis.familypoints.adapter.UsersRoutineAdapter
import com.jreis.familypoints.dto.User
import kotlinx.android.synthetic.main.fragment_routine.*

class TasksFragment : Fragment() {
    private val usersDatabase = FirebaseDatabase.getInstance().getReference("users")
    private val users = ArrayList<User>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_routine, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        usersDatabase.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(usersSnapshot: DataSnapshot) {
                for (userSnapshot in usersSnapshot.children) {
                    userSnapshot.getValue(User::class.java)?.let {
                        it.id = userSnapshot.key!!
                        users.add(it)
                    }
                }
                recyclerView_user_routines.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = UsersRoutineAdapter(users, object : OnItemClickListener<User> {
                        override fun onItemClick(position: Int) {
                            val user = users[position]
                            val intent = Intent(context, UserRoutineActivity::class.java)
                            intent.putExtra("user", user)
                            startActivity(intent)
                        }
                    })
                }
            }

        })
    }
}

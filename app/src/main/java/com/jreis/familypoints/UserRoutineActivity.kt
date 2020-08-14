package com.jreis.familypoints

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.jreis.familypoints.adapter.TaskAdapter
import com.jreis.familypoints.dto.Task
import com.jreis.familypoints.dto.User
import kotlinx.android.synthetic.main.activity_user_routine.*

class UserRoutineActivity : AppCompatActivity() {

    private val database = FirebaseDatabase.getInstance()
    private val tasks = ArrayList<Task>(20)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_routine)

        val user = intent.getSerializableExtra("user") as User

        val userRoutineDatabase = database.getReference("user_routines/" + user.id + "/monday")

        userRoutineDatabase.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(routineItemsSnapshot: DataSnapshot) {
                for (routineItem in routineItemsSnapshot.children) {
                    database.getReference("activities/" + routineItem.getValue(Long::class.java)).addListenerForSingleValueEvent(object: ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {
                            TODO("Not yet implemented")
                        }

                        override fun onDataChange(activitySnapshot: DataSnapshot) {
                            activitySnapshot.getValue(Task::class.java)?.let {
                                it.time = routineItem.key.toString()
                                it.assignee = user.firstName
                                tasks.add(it)
                            }
                        }
                    })
                }
                Thread.sleep(300)
                recyclerView_routine.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = TaskAdapter(tasks)
                }
            }

        })

    }


}
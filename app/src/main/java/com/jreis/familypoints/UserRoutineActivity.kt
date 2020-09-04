package com.jreis.familypoints

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.jreis.familypoints.adapter.TaskAdapter
import com.jreis.familypoints.contract.RoutineTaskResultContract
import com.jreis.familypoints.dto.Task
import com.jreis.familypoints.dto.TaskByTimeComparator
import com.jreis.familypoints.dto.TaskDatabaseObject
import com.jreis.familypoints.dto.User
import kotlinx.android.synthetic.main.activity_user_routine.*
import java.util.*
import kotlin.collections.ArrayList

class UserRoutineActivity : AppCompatActivity() {

    private val database = FirebaseDatabase.getInstance()
    private val tasks = TreeSet<Task>(TaskByTimeComparator())
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_routine)

        setSupportActionBar(findViewById(R.id.user_routine_toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        user = intent.getSerializableExtra("user") as User
        val userRoutineDatabase = database.getReference("user_routines/" + user.id + "/monday")

        userRoutineDatabase.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(routineItemsSnapshot: DataSnapshot) {
                for (routineItem in routineItemsSnapshot.children) {

                    routineItem.getValue(Task::class.java)?.let {
                        it.time = routineItem.key.toString()
                        it.assignee = user.firstName
                        tasks.add(it)
                    }
                }
                recyclerView_routine.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = TaskAdapter(ArrayList(tasks))
                }
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.activity_user_routine, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_add_user_activity -> {
            openCreateActivity()
        }

        else -> {
            onHomeUpOptionSelected()
        }
    }

    private fun openCreateActivity(): Boolean {
        val userRoutineDatabase = database.getReference("user_routines/" + user.id + "/monday")
        registerForActivityResult(RoutineTaskResultContract()) { task: Task? ->
            task?.let {
                userRoutineDatabase.child(it.time)
                    .setValue(TaskDatabaseObject(it.name, it.icon))
                    .addOnSuccessListener { _ ->
                        tasks.add(it)
                        recyclerView_routine.apply {
                            (adapter as TaskAdapter).addItem(it, tasks.indexOf(it))
                        }
                    }
            }
        }
        return true
    }

    private fun onHomeUpOptionSelected(): Boolean {
        super.onBackPressed()
        return true;
    }
}
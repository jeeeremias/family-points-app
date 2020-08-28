package com.jreis.familypoints

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
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

        setSupportActionBar(findViewById(R.id.user_routine_toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val user = intent.getSerializableExtra("user") as User

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
                Thread.sleep(300)
                recyclerView_routine.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = TaskAdapter(tasks)
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
        val intent = Intent(baseContext, CreateUserActivityActivity::class.java)
        startActivity(intent)
        return true
    }

    private fun onHomeUpOptionSelected(): Boolean {
        super.onBackPressed()
        return true;
    }
}
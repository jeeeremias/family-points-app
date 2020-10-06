package com.jreis.familypoints

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import com.jreis.familypoints.dto.Task

class CreateUserActivityActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user_activity)

        val saveButton = findViewById<Button>(R.id.btn_save_new_activity)
        saveButton.setOnClickListener(View.OnClickListener {
            submitActivity()
        })
    }

    private fun submitActivity() {
        val activityName = findViewById<EditText>(R.id.text_new_activity_name)
        val activityTime = findViewById<TimePicker>(R.id.time_picker_activity)

        val task = Task()
        if (Build.VERSION.SDK_INT < 23) {
            task.time = String.format("%02dh", activityTime.currentHour) + String.format("%02d", activityTime.currentMinute)
        } else {
            task.time = String.format("%02dh", activityTime.hour) + String.format("%02d", activityTime.minute)
        }
        task.name = activityName.text.toString()
        task.icon = "not_found.jpg"
        intent.putExtra("task", task)
        setResult(RESULT_OK, intent)
        finish()
    }

}
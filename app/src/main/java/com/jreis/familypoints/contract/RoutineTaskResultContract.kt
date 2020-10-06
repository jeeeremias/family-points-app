package com.jreis.familypoints.contract

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.jreis.familypoints.CreateUserActivityActivity
import com.jreis.familypoints.dto.Task

class RoutineTaskResultContract : ActivityResultContract<Set<String>, Task>() {
    override fun createIntent(context: Context, input: Set<String>): Intent {
        return Intent(context, CreateUserActivityActivity::class.java)
            .putExtra("taskTimes", ArrayList(input))
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Task {
        if (resultCode == Activity.RESULT_OK) {
            return intent?.getSerializableExtra("task") as Task
        }
        return Task()
    }
}
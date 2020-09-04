package com.jreis.familypoints.contract

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.jreis.familypoints.dto.Task

class RoutineTaskResultContract : ActivityResultContract<Set<String>, Task>() {
    override fun createIntent(context: Context, input: Set<String>): Intent {
        return Intent().putExtra("existingActivityTimes", input.toTypedArray())
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Task {
        return intent?.getSerializableExtra("task") as Task
    }
}
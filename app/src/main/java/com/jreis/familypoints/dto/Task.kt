package com.jreis.familypoints.dto

import java.io.Serializable

class Task : Serializable, Comparable<Task> {
    var name = ""
    var icon = ""
    var time = ""
    var assignee = ""

    override fun compareTo(other: Task): Int {
        return time.compareTo(other.name)
    }
}
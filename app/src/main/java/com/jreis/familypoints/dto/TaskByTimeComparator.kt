package com.jreis.familypoints.dto

class TaskByTimeComparator: Comparator<Task> {
    override fun compare(o1: Task, o2: Task): Int {
        return o1.time.compareTo(o2.time)
    }
}
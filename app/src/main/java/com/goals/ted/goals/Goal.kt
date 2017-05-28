package com.goals.ted.goals

import android.content.Context

import java.util.ArrayList
import java.util.Calendar

/**
 * Created by ted on 7/20/16.
 */
class Goal(private val context: Context, var title: String?, startDate: Calendar, endDate: Calendar) : Dates() {
    var id: Int = 0
    val allSubGoals: List<SubGoal>

    init {
        this.endDate = endDate
        this.startDate = startDate
        allSubGoals = ArrayList<SubGoal>()
    }

    fun createSubGoal(title: String, isChecked: Boolean) {
        //subGoalList.add(new SubGoal(title, isChecked));
        val myDB = MyDB(context)
    }
}

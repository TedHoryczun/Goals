package com.goals.ted.goals


/**
 * Created by ted on 7/20/16.
 */
open class Dates : GoalDates() {

    fun convert24To12Format(hour: Int): Int {
        var formattedHour = hour
        formattedHour -= 12
        if (formattedHour < 0) {
            formattedHour *= -1
        }
        return formattedHour
    }

    fun startToDueDate(): Long {
        val startTime = startDate!!.timeInMillis
        val dueDate = endDate!!.timeInMillis
        if (dueDate > startTime) {
            val result = (dueDate - startTime) / oneDayMilli
            return result
        } else {
            return 0
        }
    }

    private fun startToDueDateMIlli(): Long {
        val startTime = startDate!!.timeInMillis
        val dueDate = endDate!!.timeInMillis
        return dueDate - startTime
    }

    private fun milliToDueDate(): Long {
        val currentTime = System.currentTimeMillis()
        val dueDate = endDate!!.timeInMillis
        println("milliToDueDate" + (dueDate - currentTime))
        return dueDate - currentTime

    }

    fun daysTillDueDate(): Long {
        val currentTime = System.currentTimeMillis()
        val dueDate = endDate!!.timeInMillis
        if (dueDate > currentTime) {
            val unmeasuredResults = dueDate - currentTime
            val results = (dueDate - currentTime) / oneDayMilli
            if (unmeasuredResults < oneDayMilli && unmeasuredResults > 0) {
                return 1
            }
            return results
        } else {
            return 0
        }
    }

    fun percentageComplete(): Int {
        val daysTillDueDate = milliToDueDate()
        val startToDueDate = startToDueDateMIlli()
        var percentCompleted = 0

        try {
            if (isGoalCompleted(milliToDueDate())) {
                percentCompleted = 100
            } else {
                percentCompleted = calculatePercentage(startToDueDate, daysTillDueDate)
            }
        } catch (e: ArithmeticException) {
            percentCompleted = 100
        }

        return percentCompleted
    }

    private fun isGoalCompleted(daysTillDueDate: Long): Boolean {
        val isCompleted: Boolean = daysTillDueDate == GOAL_COMPLETE.toLong()
                || daysTillDueDate < 0
        return isCompleted
    }

    private fun calculatePercentage(startToDueDate: Long, daysTillDueDate: Long): Int {
        var percentage = 0
        val daysCompleted = (startToDueDate - daysTillDueDate).toInt()
        percentage = (daysCompleted * 100 / startToDueDate).toInt()
        return percentage
    }

    companion object {
        var oneDayMilli: Long = 86400000
        private val GOAL_COMPLETE = 0
    }


}

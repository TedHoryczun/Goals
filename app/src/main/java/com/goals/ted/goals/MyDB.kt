package com.goals.ted.goals

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log

import java.util.ArrayList
import java.util.Calendar


/**
 * Created by ted on 7/25/16.
 */
class MyDB(private val context: Context) {
    private val helper: DatabaseHelper = DatabaseHelper(context)
    private val database: SQLiteDatabase


    init {
        database = helper.writableDatabase
    }

    fun closeDb() {
        database.close()
    }

    fun subGoalUpdate(key: String, subGoalId: Int, content: String) {
        val values = ContentValues()
        values.put(key, content)
        database.update(SUB_TABLE, values, SUB_ID + "=?", arrayOf(subGoalId.toString()))
    }

    fun changeCheckedSubGoal(id: Int, isChecked: Boolean) {
        val values = ContentValues()
        if (isChecked) {
            values.put(SUB_CHECKED, 1)
            println("true")
        } else {
            values.put(SUB_CHECKED, 0)
            println("false")
        }
        database.update(SUB_TABLE, values, SUB_ID + "=?", arrayOf(id.toString()))

    }

    fun createSubGoal(goalId: Int, title: String, isChecked: Boolean) {
        val values = ContentValues()
        values.put(SUB_TITLE, title)
        values.put(SUB_GOALID, goalId)
        if (isChecked) {

            values.put(SUB_CHECKED, 1)
        } else {
            values.put(SUB_CHECKED, 0)

        }
        database.insert(SUB_TABLE, null, values)
    }

    fun createRecord(title: String, createdMilli: Long, dueDateMilli: Long): Long {
        val values = ContentValues()
        values.put(EMP_TITLE, title)
        values.put(EMP_CREATED, createdMilli)
        values.put(EMP_DUEDATE, dueDateMilli)
        return database.insert(EMP_TABLE, null, values)

    }

    fun selectSubGoalsById(id: Int): List<SubGoal> {
        val cols = arrayOf(SUB_ID, SUB_GOALID, SUB_TITLE, SUB_CHECKED)
        val subGoalList = ArrayList<SubGoal>()
        val mCursor = database.query(true, SUB_TABLE, cols,
                SUB_GOALID + "=?", arrayOf(id.toString()), null, null, null, null)
        if (mCursor.moveToFirst()) {
            do {
                val subId = mCursor.getInt(0)
                val goalId = mCursor.getInt(1)
                val subTitle = mCursor.getString(2)
                val isChecked = mCursor.getInt(3)
                val isCheckBoolean: Boolean
                isCheckBoolean = isChecked != 0
                Log.i("isCheckBoolean: ", isCheckBoolean.toString())
                val subGoal = SubGoal(subId, subTitle, isCheckBoolean)
                subGoal.id = subId
                subGoalList.add(subGoal)
            } while (mCursor.moveToNext())
        }
        return subGoalList

    }

    fun selectByID(id: Int?): Goal? {
        val cols = arrayOf(EMP_ID, EMP_TITLE, EMP_CREATED, EMP_DUEDATE)

        val mCursor = database.query(true, EMP_TABLE, cols,
                "$EMP_ID=?", arrayOf(id.toString()), null, null, null, null)

        var goal: Goal? = null
        if (mCursor.moveToFirst() && id != null) {
            do {
                val title = mCursor.getString(1)
                val created = mCursor.getLong(2)
                val dueDate = mCursor.getLong(3)
                val calendarCreated = Calendar.getInstance()
                val calendarDue = Calendar.getInstance()
                calendarCreated.timeInMillis = created
                calendarDue.timeInMillis = dueDate
                goal = Goal(context, title, calendarCreated, calendarDue)
                goal.id = id
                return goal
            } while (mCursor.moveToNext())
        }
        return goal
    }

    fun selectRecords(): MutableList<Goal> {
        val cols = arrayOf(EMP_ID, EMP_TITLE, EMP_CREATED, EMP_DUEDATE)
        val goals = ArrayList<Goal>()
        val mCursor = database.query(true, EMP_TABLE, cols, null, null, null, null, null, null)
        if (mCursor.moveToFirst()) {
            do {
                val title = mCursor.getString(1)
                val created = mCursor.getLong(2)
                val dueDate = mCursor.getLong(3)
                val calendarCreated = Calendar.getInstance()
                val calendarDue = Calendar.getInstance()
                calendarCreated.timeInMillis = created
                calendarDue.timeInMillis = dueDate

                val goal = Goal(context, title, calendarCreated, calendarDue)
                goal.id = mCursor.getInt(0)
                goals.add(goal)

            } while (mCursor.moveToNext())
        }
        return goals
    }

    fun deleteRecord(table: String, id: Int) {
        database.delete(table, EMP_ID + " =" + id, null)
    }

    companion object {
        val EMP_ID = "id"
        val EMP_TABLE = "Goals"
        val EMP_TITLE = "title"
        val EMP_CREATED = "created"
        val EMP_DUEDATE = "dueDate"

        val SUB_TITLE = "title"
        val SUB_CHECKED = "isChecked"
        val SUB_ID = "id"
        val SUB_GOALID = "goalID"
        val SUB_TABLE = "SubGoals"
    }
    //public void deleteSubGoal(int id){
    //database.delete(SUB_TABLE, SUB_ID + " =" + id, null);
    //}
}

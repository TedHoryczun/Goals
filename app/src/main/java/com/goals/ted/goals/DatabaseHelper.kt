package com.goals.ted.goals

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Created by ted on 7/25/16.
 */
class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DatabaseHelper.DATABASE_NAME, null, DatabaseHelper.DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(DATABASE_CREATE)
        db.execSQL(SUBGOAL_DATABASE)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("drop table if exists Goals")

    }

    companion object {
        private val DATABASE_NAME = "goal.db"
        private val DATABASE_VERSION = 1
        private val DATABASE_CREATE = "create table Goals" + "(id Integer PRIMARY KEY AUTOINCREMENT, title text, created long, dueDate long)"
        private val SUBGOAL_DATABASE = "create table SubGoals" + "(id Integer Primary KEY AUTOINCREMENT, goalID Integer, title text, isChecked Integer)"
    }
}

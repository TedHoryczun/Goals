package com.goals.ted.goals;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ted on 7/25/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "goal.db";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_CREATE = "create table Goals" +
            "(id Integer PRIMARY KEY AUTOINCREMENT, title text, created long, dueDate long)";
    private static final String SUBGOAL_DATABASE = "create table SubGoals" +
            "(id Integer Primary KEY AUTOCINCREMENT, goalID Integer, title text, isChecked Integer)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
        db.execSQL(SUBGOAL_DATABASE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Goals");

    }
}

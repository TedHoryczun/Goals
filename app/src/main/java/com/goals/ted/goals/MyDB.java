package com.goals.ted.goals;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Created by ted on 7/25/16.
 */
public class MyDB {
    private DatabaseHelper helper;
    private SQLiteDatabase database;
    public final static String EMP_ID = "id";
    public final static String EMP_TABLE="Goals";
    private final static String EMP_TITLE = "title";
    private final static String EMP_CREATED = "created";
    private final static String EMP_DUEDATE = "dueDate";

    private Calendar calendarCreated;
    private Calendar calendarDue;

    public MyDB(Context context){
        helper = new DatabaseHelper(context);
        database = helper.getWritableDatabase();
    }
    public long createRecord(String title, long createdMilli, long dueDateMilli){
        ContentValues values = new ContentValues();
        values.put(EMP_TITLE, title);
        values.put(EMP_CREATED, createdMilli);
        values.put(EMP_DUEDATE, dueDateMilli);
        return database.insert(EMP_TABLE, null, values);

    }
    public List<Goal> selectRecords(){
        String[] cols = new String[]{EMP_ID, EMP_TITLE, EMP_CREATED, EMP_DUEDATE};
        List<Goal> goals = new ArrayList<>();
        Cursor mCursor = database.query(true, EMP_TABLE, cols,
                null, null, null, null, null, null);
        if(mCursor.moveToFirst()){
            do{
                String title = mCursor.getString(1);
                long created = mCursor.getLong(2);
                long dueDate = mCursor.getLong(3);
                calendarCreated = Calendar.getInstance();
                calendarDue = Calendar.getInstance();
                calendarCreated.setTimeInMillis(created);
                calendarDue.setTimeInMillis(dueDate);


                Goal goal = new Goal(title, calendarCreated, calendarDue);
                goal.setId(mCursor.getInt(0));
                goals.add(goal);

            }while(mCursor.moveToNext());
        }
        return goals;
    }
    public void deleteRecord(int id){
        database.delete(EMP_TABLE, EMP_ID + " =?", new String[]{String.valueOf(id)});
        database.close();
    }
}

package com.goals.ted.goals;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Created by ted on 7/25/16.
 */
public class MyDB {
    private DatabaseHelper helper;
    private SQLiteDatabase database;
    private Context context;
    public final static String EMP_ID = "id";
    public final static String EMP_TABLE="Goals";
    public final static String EMP_TITLE = "title";
    public final static String EMP_CREATED = "created";
    public final static String EMP_DUEDATE = "dueDate";

    public final static String SUB_TITLE = "title";
    public final static String SUB_CHECKED = "isChecked";
    public final static String SUB_ID = "id";
    public final static String SUB_GOALID = "goalID";
    public final static String SUB_TABLE = "SubGoals";


    public MyDB(Context context){
        helper = new DatabaseHelper(context);
        database = helper.getWritableDatabase();
        this.context = context;
    }
    public void closeDb(){
        database.close();
    }
    public void subGoalUpdate(String key, int subGoalId, String content){
        ContentValues values = new ContentValues();
        values.put(key, content);
        database.update(SUB_TABLE, values, SUB_ID +"=?", new String[]{String.valueOf(subGoalId)});
    }
    public void changeCheckedSubGoal(int id, boolean isChecked){
        ContentValues values = new ContentValues();
        if(isChecked== true){
            values.put(SUB_CHECKED, 1);
            System.out.println("true");
        }else{
            values.put(SUB_CHECKED, 0);
            System.out.println("false");
        }
        database.update(SUB_TABLE, values, SUB_ID +"=?", new String[]{String.valueOf(id)});

    }
    public void createSubGoal(int goalId, String title, boolean isChecked){
        ContentValues values = new ContentValues();
        values.put(SUB_TITLE, title);
        values.put(SUB_GOALID, goalId);
        if(isChecked == true){

            values.put(SUB_CHECKED, 1);
        }else{
            values.put(SUB_CHECKED, 0);

        }
        database.insert(SUB_TABLE, null, values);
    }
    public long createRecord(String title, long createdMilli, long dueDateMilli){
        ContentValues values = new ContentValues();
        values.put(EMP_TITLE, title);
        values.put(EMP_CREATED, createdMilli);
        values.put(EMP_DUEDATE, dueDateMilli);
        return database.insert(EMP_TABLE, null, values);

    }
    public List<SubGoal> selectSubGoalsById(int id){
        String[] cols = new String[]{SUB_ID, SUB_GOALID,SUB_TITLE, SUB_CHECKED};
        List<SubGoal> subGoalList = new ArrayList<>();
        Cursor mCursor = database.query(true, SUB_TABLE, cols,
                SUB_GOALID + "=?", new String[]{String.valueOf(id)},
                null, null, null, null);
        if(mCursor.moveToFirst()){
            do{
                int subId = mCursor.getInt(0);
                int goalId = mCursor.getInt(1);
                String subTitle = mCursor.getString(2);
                int isChecked = mCursor.getInt(3);
                boolean isCheckBoolean;
                if(isChecked==0){
                   isCheckBoolean = false;
                }else{
                    isCheckBoolean = true;
                }
                Log.i("isCheckBoolean: ", String.valueOf(isCheckBoolean));
                SubGoal subGoal = new SubGoal(subId, subTitle, isCheckBoolean);
                subGoal.setId(subId);
                subGoalList.add(subGoal);
            }while(mCursor.moveToNext());
        }
        return subGoalList;

    }
    public Goal selectByID(int id){
        String[] cols = new String[]{EMP_ID, EMP_TITLE, EMP_CREATED, EMP_DUEDATE};
        Cursor mCursor = database.query(true, EMP_TABLE, cols,
                EMP_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null, null);

        Calendar calendarCreated = Calendar.getInstance();
        Calendar calendarDue = Calendar.getInstance();
        Goal goal = null;
        if(mCursor.moveToFirst()){
            do{
                String title = mCursor.getString(1);
                long created = mCursor.getLong(2);
                long dueDate = mCursor.getLong(3);
                calendarCreated = Calendar.getInstance();
                calendarDue = Calendar.getInstance();
                calendarCreated.setTimeInMillis(created);
                calendarDue.setTimeInMillis(dueDate);
                goal = new Goal(context, title, calendarCreated, calendarDue);
                goal.setId(id);
                return goal;
            }while(mCursor.moveToNext());
        }
        return goal;
    }
    public List<Goal> selectRecords(){
        String[] cols = new String[]{EMP_ID, EMP_TITLE, EMP_CREATED, EMP_DUEDATE};
        List<Goal> goals = new ArrayList<>();
        Cursor mCursor = database.query(true, EMP_TABLE, cols,
                null, null, null, null, null, null);
        Calendar calendarCreated = Calendar.getInstance();
        Calendar calendarDue = Calendar.getInstance();
        if(mCursor.moveToFirst()){
            do{
                String title = mCursor.getString(1);
                long created = mCursor.getLong(2);
                long dueDate = mCursor.getLong(3);
                calendarCreated = Calendar.getInstance();
                calendarDue = Calendar.getInstance();
                calendarCreated.setTimeInMillis(created);
                calendarDue.setTimeInMillis(dueDate);

                Goal goal = new Goal(context, title, calendarCreated, calendarDue);
                goal.setId(mCursor.getInt(0));
                goals.add(goal);

            }while(mCursor.moveToNext());
        }
        return goals;
    }
    public void deleteRecord(String table, int id){
        database.delete(table, EMP_ID + " =" + id, null);
    }
    //public void deleteSubGoal(int id){
        //database.delete(SUB_TABLE, SUB_ID + " =" + id, null);
    //}
}

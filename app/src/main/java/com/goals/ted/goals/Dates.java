package com.goals.ted.goals;

import android.util.Log;

/**
 * Created by ted on 7/20/16.
 */
public class Dates extends GoalDates{
    public static long oneDayMilli = 86400000;
    public long startToDueDate(){
        long startTime = getStartDate().getTimeInMillis();
        long dueDate = getEndDate().getTimeInMillis();
        if(dueDate > startTime){
            long result = (dueDate - startTime)/oneDayMilli;
            return result;
        }else{
            return 0;
        }
    }
    public long daysTillDueDate(){
        long currentTime = System.currentTimeMillis();
        long dueDate = getEndDate().getTimeInMillis();
        if(dueDate > currentTime){
            long results = (dueDate - currentTime)/oneDayMilli;
            return results;
        }else{
            return 0;
        }
    }
    public double percentageComplete(){
        Log.i("daysTillDueDate: ", String.valueOf(daysTillDueDate()));
        Log.i("startToDueDate: ", String.valueOf(startToDueDate()));
        long daysTill = daysTillDueDate();
        long startTo = startToDueDate();
        if(daysTill<startToDueDate()){
            daysTill=+1;

        }

        return daysTill*100/startTo;
    }

}

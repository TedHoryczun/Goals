package com.goals.ted.goals;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by ted on 7/20/16.
 */
public class Dates extends GoalDates{
    public static long oneDayMilli = 86400000;
    private static final int GOAL_COMPLETE = 0;

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
    public int percentageComplete(){
        long daysTillDueDate = daysTillDueDate();
        long startToDueDate = startToDueDate();
        int percentCompleted;

        try{
            if(isGoalCompleted(daysTillDueDate)){
            percentCompleted = 100;
            }else{
                percentCompleted = calculatePercentage(startToDueDate, daysTillDueDate);
            }
        }catch (ArithmeticException e){
            percentCompleted = 100;
        }

        return percentCompleted;
    }
    private boolean isGoalCompleted(long daysTillDueDate){
        boolean isCompleted;
        if(daysTillDueDate == GOAL_COMPLETE){
            isCompleted = true;
        }else{
            isCompleted = false;
        }
        return isCompleted;
    }
    private int calculatePercentage(long startToDueDate, long daysTillDueDate){
        int percentage = 0;
        int daysCompleted = (int) (startToDueDate - daysTillDueDate);
        percentage = (int) (daysCompleted*100/startToDueDate);
        return percentage;
    }


}

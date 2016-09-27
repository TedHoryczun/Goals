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

    public int convert24To12Format(int hour){
        int formattedHour = hour;
        formattedHour -= 12;
        if(formattedHour < 0){
            formattedHour *= -1;
        }
        return formattedHour;
    }

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
    private long startToDueDateMIlli(){
        long startTime = getStartDate().getTimeInMillis();
        long dueDate = getEndDate().getTimeInMillis();
        return dueDate - startTime;
    }
    private long milliToDueDate(){
        long currentTime = System.currentTimeMillis();
        long dueDate = getEndDate().getTimeInMillis();
        System.out.println("milliToDueDate" + (dueDate - currentTime));
        return dueDate - currentTime;

    }
    public long daysTillDueDate(){
        long currentTime = System.currentTimeMillis();
        long dueDate = getEndDate().getTimeInMillis();
        if(dueDate > currentTime){
            long unmeasuredResults = dueDate - currentTime;
            long results = (dueDate - currentTime)/oneDayMilli;
            if(unmeasuredResults < oneDayMilli && unmeasuredResults > 0){
                return 1;
            }
            return results;
        }else{
            return 0;
        }
    }
    public int percentageComplete(){
        long daysTillDueDate = milliToDueDate();
        long startToDueDate = startToDueDateMIlli();
        int percentCompleted = 0;

        try{
            if(isGoalCompleted(milliToDueDate())){
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
        if(daysTillDueDate == GOAL_COMPLETE || daysTillDueDate < 0){
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

package com.goals.ted.goals;

import android.util.Log;

import java.util.List;

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
        long daysTill = daysTillDueDate();
        long startTo = startToDueDate();
        if(daysTill == 0){
            return 100.00;
        }
        float obtained;
        try{
            int daysCompleted = (int) (startTo - daysTill);
            obtained = daysCompleted*100/startTo;
        }catch (ArithmeticException e){
            return 100.00;
        }

        return obtained;
    }

}

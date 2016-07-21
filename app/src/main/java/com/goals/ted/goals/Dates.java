package com.goals.ted.goals;

/**
 * Created by ted on 7/20/16.
 */
public class Dates extends GoalDates{
    private long oneDayMilli = 86400000;
    public int startToDueDate(){
        long startTime = getStartDate().getTimeInMillis();
        long dueDate = getEndDate().getTimeInMillis();
        if(dueDate > startTime){
            return (int) ((dueDate - startTime)%oneDayMilli);
        }else{
            return 0;
        }
    }
    public int daysTillDueDate(){
        long currentTime = System.currentTimeMillis();
        long dueDate = getEndDate().getTimeInMillis();
        if(dueDate > currentTime){
            return (int) ((dueDate - currentTime)%oneDayMilli);
        }else{
            return 0;
        }
    }
    public double percentageComplete(){
        return double percentage = daysTillDueDate()/startToDueDate();
    }

}

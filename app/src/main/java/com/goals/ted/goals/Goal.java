package com.goals.ted.goals;

import java.util.Calendar;

/**
 * Created by ted on 7/20/16.
 */
public class Goal extends Dates{
    private int id;
    private String title;
    private GoalDates goalDates;

    public Goal(String title, Calendar startDate, Calendar endDate){
        this.title = title;
        this.setEndDate(endDate);
        this.setStartDate(startDate);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public GoalDates getGoalDates() {
        return goalDates;
    }

    public void setGoalDates(GoalDates goalDates) {
        this.goalDates = goalDates;
    }
}

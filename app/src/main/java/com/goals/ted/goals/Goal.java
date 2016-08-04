package com.goals.ted.goals;

import android.content.Context;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by ted on 7/20/16.
 */
public class Goal extends Dates{
    private int id;
    private String title;
    private GoalDates goalDates;
    private List<SubGoal> subGoalList;
    private Context context;

    public Goal(Context context, String title, Calendar startDate, Calendar endDate){
        this.title = title;
        this.setEndDate(endDate);
        this.setStartDate(startDate);
        subGoalList = new ArrayList<>();
        this.context = context;
    }
    public void createSubGoal(String title, boolean isChecked){
        //subGoalList.add(new SubGoal(title, isChecked));
        MyDB myDB = new MyDB(context);
    }
    public List<SubGoal> getAllSubGoals(){
        return subGoalList;
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

package com.goals.ted.goals;

import java.util.Calendar;

/**
 * Created by ted on 7/20/16.
 */
public class GoalDatesKotlin {
    private Calendar startDate;

    public Calendar getEndDate() {
        return EndDate;
    }

    public void setEndDate(Calendar endDate) {
        EndDate = endDate;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    private Calendar EndDate;
}

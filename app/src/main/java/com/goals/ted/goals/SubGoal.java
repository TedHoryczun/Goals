package com.goals.ted.goals;

/**
 * Created by ted on 7/31/16.
 */
public class SubGoal {
    private String title;
    private boolean isChecked;
    public SubGoal(String title, boolean isChecked){
        this.title = title;
        this.isChecked = isChecked;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}

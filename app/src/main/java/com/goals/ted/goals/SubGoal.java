package com.goals.ted.goals;

public class SubGoal {
    private String title;
    private boolean isChecked;
    private int id;
    public SubGoal(int id, String title, boolean isChecked){
        this.title = title;
        this.isChecked = isChecked;
        this.id = id;
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

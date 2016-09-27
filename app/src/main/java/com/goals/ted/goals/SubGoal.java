package com.goals.ted.goals;

public class SubGoal {
    private String title;
    private boolean isChecked;

    public boolean isEditMode() {
        return isEditMode;
    }

    public void setEditMode(boolean editMode) {
        isEditMode = editMode;
    }

    private boolean isEditMode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    @Override
    public String toString() {
        return getTitle();
    }
}

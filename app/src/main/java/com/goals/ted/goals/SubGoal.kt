package com.goals.ted.goals

class SubGoal(var id: Int, var title: String = "", var isChecked: Boolean) {

    var isEditMode: Boolean = false

    override fun toString(): String {
        return title
    }
}

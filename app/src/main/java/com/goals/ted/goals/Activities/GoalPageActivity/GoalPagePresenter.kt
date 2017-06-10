package com.goals.ted.goals.Activities.GoalPageActivity

import android.content.Context
import com.goals.ted.goals.Fragments.GoalPageFragment
import com.goals.ted.goals.Goal
import com.goals.ted.goals.MyDB

/**
 * Created by ted on 6/9/17.
 */
class GoalPagePresenter(val view: GoalPageMVP.View, val context: Context): GoalPageMVP.Presenter{


    private val db: MyDB by lazy{ MyDB(context) }
    private var goal: Goal = Goal(context)
    override fun displayActionBar() {
        val goalTitle = goal.title
        view.displayActionBar(goalTitle)
    }

    override fun getGoalFromDB(id: Int) {
        val goal = db.selectByID(id)
        if(goal != null){this.goal = goal}
    }
    override fun displayProgress() {
       val percentGoalCompletedFromChecklist = goal.percentageComplete()
        view.displayGoalProgress(percentGoalCompletedFromChecklist)

    }
    override fun displayGoal(id: Int) {
        view.displayGoal(id)
    }
}

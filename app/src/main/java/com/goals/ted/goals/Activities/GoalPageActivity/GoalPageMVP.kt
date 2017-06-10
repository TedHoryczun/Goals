package com.goals.ted.goals.Activities.GoalPageActivity

/**
 * Created by ted on 6/9/17.
 */
class GoalPageMVP{
    interface View{
        fun displayActionBar(goalTitle: String?)
        fun  displayGoalProgress(percentGoalCompletedFromChecklist: Int)
        fun  displayGoal(id: Int)

    }
    interface Presenter{
        fun displayActionBar()
        fun  getGoalFromDB(id: Int)
        fun displayProgress()
        fun displayGoal(id: Int)

    }
}

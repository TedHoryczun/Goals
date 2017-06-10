package com.goals.ted.goals.Activities.GoalPageActivity

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem

import com.dinuscxj.progressbar.CircleProgressBar
import com.goals.ted.goals.Fragments.GoalPageFragment
import com.goals.ted.goals.R
import kotlinx.android.synthetic.main.activity_goal_page.*

class GoalPage : AppCompatActivity(), GoalPageFragment.OnFragmentInteractionListener, GoalPageMVP.View {

    private var goalPagePercentComplete: CircleProgressBar? = null
    val bundle: Bundle by lazy { intent.extras }
    val presenter by lazy{GoalPagePresenter(this, applicationContext)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal_page)
        val id = bundle.getInt("id")
        presenter.getGoalFromDB(id)
        presenter.displayActionBar()
        presenter.displayProgress()
        presenter.displayGoal(id)
    }

    override fun displayActionBar(goalTitle: String?) {
        app_bar.title = goalTitle
        setSupportActionBar(app_bar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun displayGoalProgress(percentGoalCompletedFromChecklist: Int) {
        goalPagePercentComplete?.progress = percentGoalCompletedFromChecklist
    }
    override fun displayGoal(id: Int) {
        val fragment = GoalPageFragment.newInstance(id.toString(), "hello")
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment)
                .commit()
        goalPageAddSubGoal.setOnClickListener({fragment.createSubGoal()})
    }
    override fun onFragmentInteraction(uri: Uri) {

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

}

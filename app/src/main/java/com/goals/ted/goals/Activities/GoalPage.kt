package com.goals.ted.goals.Activities

import android.content.Intent
import android.net.Uri
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView

import com.dinuscxj.progressbar.CircleProgressBar
import com.goals.ted.goals.Fragments.GoalPageFragment
import com.goals.ted.goals.Goal
import com.goals.ted.goals.MyDB
import com.goals.ted.goals.R
import com.goals.ted.goals.SubGoal
import com.goals.ted.goals.SubGoalAdapter
import kotlinx.android.synthetic.main.activity_goal_page.*

class GoalPage : AppCompatActivity(), GoalPageFragment.OnFragmentInteractionListener {
    private val db: MyDB by lazy{MyDB(applicationContext)}
    private var goal: Goal? = null
    private var goalPagePercentComplete: CircleProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal_page)

        val bundle = intent.extras
        val id = bundle.getInt("id")
        goal = db.selectByID(id)

        val goalTitle = goal!!.title
        app_bar.title = goalTitle
        setSupportActionBar(app_bar)

        val ab = supportActionBar
        ab?.setDisplayHomeAsUpEnabled(true)

        goalPagePercentComplete?.progress = goal?.percentageComplete() as Int
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

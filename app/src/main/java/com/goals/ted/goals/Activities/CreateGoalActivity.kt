package com.goals.ted.goals.Activities

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar

import com.goals.ted.goals.Fragments.CreateGoalFragment
import com.goals.ted.goals.R
import kotlinx.android.synthetic.main.activity_create_goal.*

class CreateGoalActivity : AppCompatActivity(), CreateGoalFragment.OnFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_goal)
        setSupportActionBar(app_bar as Toolbar?)
        val ab = supportActionBar
        ab?.setDisplayHomeAsUpEnabled(true)
        ab?.setHomeAsUpIndicator(R.drawable.ic_close_white_24dp)
        supportFragmentManager.beginTransaction().replace(R.id.container, CreateGoalFragment())
                .commit()
    }

    override fun onFragmentInteraction(uri: Uri) {

    }


}

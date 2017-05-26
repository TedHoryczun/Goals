package com.goals.ted.goals.Activities

import android.content.Intent
import android.net.Uri
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.Toast

import com.goals.ted.goals.Fragments.FragmentList
import com.goals.ted.goals.Goal
import com.goals.ted.goals.R
import com.goals.ted.goals.GoalAdapter

class MainActivity : AppCompatActivity(), FragmentList.OnFragmentInteractionListener {
    private val goal: Goal? = null
    private val recyclerView: RecyclerView? = null
    private val adapter: GoalAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.my_toolbar) as Toolbar?
        setSupportActionBar(toolbar)
        val fab = findViewById(R.id.fab) as FloatingActionButton?
        fab!!.setOnClickListener {
            val intent = Intent(applicationContext, CreateGoalActivity::class.java)

            startActivity(intent)
            Log.d("onClicked", "fab clicked")
        }
        supportFragmentManager.beginTransaction().replace(R.id.container, FragmentList())
                .commit()
    }

    override fun onFragmentInteraction(uri: Uri) {

    }
}

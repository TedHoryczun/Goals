package com.goals.ted.goals.Activities

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.util.Log
import com.goals.ted.goals.Fragments.FragmentList
import com.goals.ted.goals.R
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.intentFor

class MainActivity : AppCompatActivity(), FragmentList.OnFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(my_toolbar as Toolbar?)
        fab.setOnClickListener {
            startActivity(intentFor<CreateGoalActivity>())
            Log.d("onClicked", "fab clicked")
        }
        supportFragmentManager.beginTransaction().replace(R.id.container, FragmentList())
                .commit()
    }

    override fun onFragmentInteraction(uri: Uri) {

    }
}

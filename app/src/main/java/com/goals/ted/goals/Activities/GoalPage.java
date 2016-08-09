package com.goals.ted.goals.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.goals.ted.goals.Fragments.GoalPageFragment;
import com.goals.ted.goals.R;

public class GoalPage extends AppCompatActivity implements GoalPageFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int id = bundle.getInt("id");
        Fragment fragment = GoalPageFragment.newInstance(String.valueOf(id),  "hello");
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment)
        .commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}

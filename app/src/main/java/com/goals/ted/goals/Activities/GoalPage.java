package com.goals.ted.goals.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.goals.ted.goals.Fragments.GoalPageFragment;
import com.goals.ted.goals.Goal;
import com.goals.ted.goals.MyDB;
import com.goals.ted.goals.R;
import com.goals.ted.goals.SubGoal;
import com.goals.ted.goals.SubGoalAdapter;

public class GoalPage extends AppCompatActivity implements GoalPageFragment.OnFragmentInteractionListener{
    private MyDB db;
    private Goal goal;
    private FloatingActionButton createSubGoal;

    private TextView percentTaskCompleted;
    private TextView goalTitleTxtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_page);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int id = bundle.getInt("id");
        db = new MyDB(getApplicationContext());
        goal = db.selectByID(id);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        String goalTitle = goal.getTitle();
        toolbar.setTitle(goalTitle);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        percentTaskCompleted = (TextView) findViewById(R.id.goalPagePercentComplete);
        percentTaskCompleted.setText(goal.percentageComplete() + "%");
        Fragment fragment = GoalPageFragment.newInstance(String.valueOf(id),  "hello");
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment)
        .commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}

package com.goals.ted.goals.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.goals.ted.goals.Fragments.CreateGoalFragment;
import com.goals.ted.goals.Fragments.FragmentList;
import com.goals.ted.goals.Goal;
import com.goals.ted.goals.R;
import com.goals.ted.goals.RecyclerAdapter;

public class MainActivity extends AppCompatActivity implements  FragmentList.OnFragmentInteractionListener{
    private Goal goal;
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateGoalActivity.class);
                startActivity(intent);
            }
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new FragmentList())
                .commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

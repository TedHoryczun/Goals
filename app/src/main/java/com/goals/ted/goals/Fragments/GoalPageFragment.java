package com.goals.ted.goals.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.goals.ted.goals.Goal;
import com.goals.ted.goals.MyDB;
import com.goals.ted.goals.R;
import com.goals.ted.goals.SubGoal;
import com.goals.ted.goals.SubGoalAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GoalPageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GoalPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GoalPageFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String id;
    private String mParam2;

    private ImageButton addSubGoal;
    private ArrayList<SubGoal> subGoalList;
    private SubGoalAdapter adapter;
    private MyDB myDB;
    private Goal goal;

    private TextView title;
    private TextView dueDate;

    private OnFragmentInteractionListener mListener;

    public GoalPageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GoalPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GoalPageFragment newInstance(String param1, String param2) {
        GoalPageFragment fragment = new GoalPageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        myDB = new MyDB(getActivity());
        goal = myDB.selectByID(Integer.parseInt(id));
        subGoalList = (ArrayList<SubGoal>) myDB.selectSubGoals(Integer.parseInt(id));

        View view = inflater.inflate(R.layout.fragment_goal_page, container, false);
        title = (TextView) view.findViewById(R.id.title);
        title.setText(goal.getTitle());
        dueDate = (TextView) view.findViewById(R.id.dueDate);
        dueDate.setText("Due in " + String.valueOf(goal.daysTillDueDate()) + " days");

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.subGoalRecyclerView);
        addSubGoal = (ImageButton) view.findViewById(R.id.addSubGoal);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new SubGoalAdapter(getActivity(), this.subGoalList);
        recyclerView.setAdapter(adapter);
        createSubGoal();
        return view;
    }
    public void createSubGoal(){
        addSubGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDB.createSubGoal(goal.getId(), "", false);
                subGoalList.add(new SubGoal(goal.getId(), "", false));
                adapter.notifyItemInserted(subGoalList.size()+1);

            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.create_gest_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.save:
                    SubGoalAdapter.onSave();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

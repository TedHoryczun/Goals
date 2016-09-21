package com.goals.ted.goals.Fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.goals.ted.goals.Activities.MainActivity;
import com.goals.ted.goals.DateFormatter;
import com.goals.ted.goals.Goal;
import com.goals.ted.goals.MyDB;
import com.goals.ted.goals.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateGoalFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateGoalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateGoalFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText timePicker;
    private EditText datePicker;
    private EditText title;

    private Calendar currentTime;
    private Calendar dueDate;

    private OnFragmentInteractionListener mListener;

    public CreateGoalFragment() {
        // Required empty public constructor
    }



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateGoalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateGoalFragment newInstance(String param1, String param2) {
        CreateGoalFragment fragment = new CreateGoalFragment();
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=  inflater.inflate(R.layout.fragment_create_goal, container, false);
        setHasOptionsMenu(true);
        currentTime = Calendar.getInstance();
        currentTime.setTimeInMillis(System.currentTimeMillis());
        String formattedCurrentDate = DateFormatter.convertDateToDateFormat(currentTime);
        timePicker = (EditText) v.findViewById(R.id.timePicker);
        datePicker = (EditText)v.findViewById(R.id.datePicker);
        datePicker.setText(formattedCurrentDate);
        title = (EditText) v.findViewById(R.id.goalTitle);
        dueDate = Calendar.getInstance();
        timePickerDialog(v);
        datePickerDialog(v);
        return v;
    }
    public void datePickerDialog(View v){
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        dueDate.set(Calendar.YEAR, year);
                        dueDate.set(Calendar.MONTH, monthOfYear);
                        dueDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String formattedDate = DateFormatter.convertDateToDateFormat(dueDate);
                        datePicker.setText(formattedDate);


                    }
                }, currentTime.get(Calendar.YEAR), currentTime.get(Calendar.MONTH), currentTime.get(Calendar.DAY_OF_MONTH));
                        dialog.show();
            }
        });

    }
    public void timePickerDialog(View v){
        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog dialog = new TimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        dueDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        dueDate.set(Calendar.MINUTE, minute);
                        timePicker.setText(hourOfDay + ":"+ minute);

                    }
                }, currentTime.get(Calendar.HOUR_OF_DAY), currentTime.get(Calendar.MINUTE), false);
                dialog.show();

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
                String titleTxt = title.getText().toString();
                if(!titleTxt.isEmpty()){
                    Goal goal = new Goal(getActivity(), titleTxt, currentTime, dueDate);
                    MyDB db = new MyDB(getContext());
                    db.createRecord(titleTxt, currentTime.getTimeInMillis(), dueDate.getTimeInMillis());
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getActivity(), "You can't have a empty title", Toast.LENGTH_SHORT).show();

                }
                break;
        }
        return super.onOptionsItemSelected(item);

    }
}

package com.goals.ted.goals.Fragments.CreateGoal

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.goals.ted.goals.Activities.MainActivity

import com.goals.ted.goals.DateFormatter
import com.goals.ted.goals.MyDB
import com.goals.ted.goals.R
import kotlinx.android.synthetic.main.fragment_create_goal.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast

import java.util.Calendar

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [CreateGoalFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [CreateGoalFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreateGoalFragment : Fragment(), CreateGoalMVP.View {

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null
    private var currentTime: Calendar? = null
    private var dueDate: Calendar = Calendar.getInstance()
    private var mListener: OnFragmentInteractionListener? = null
    val presenter by lazy {CreateGoalPresenter(context, this)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments.getString(ARG_PARAM1)
            mParam2 = arguments.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v = inflater!!.inflate(R.layout.fragment_create_goal, container, false)
        setHasOptionsMenu(true)

        return v
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentTime = Calendar.getInstance()
        currentTime?.timeInMillis = System.currentTimeMillis()
        val formattedCurrentDate = DateFormatter.convertDateToDateFormat(currentTime)
        datePicker.setText(formattedCurrentDate)

        timePicker.setOnClickListener {
            presenter.launchTimePicker()
        }
        datePicker.setOnClickListener {
            presenter.launchDatePicker()
        }
    }

    override fun launchDatePicker() {
        val dialog = DatePickerDialog(context, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            dueDate = presenter.setDate(year, monthOfYear, dayOfMonth)
            val formattedDate = DateFormatter.convertDateToDateFormat(dueDate)
            datePicker?.setText(formattedDate)
        }, currentTime!!.get(Calendar.YEAR), currentTime!!.get(Calendar.MONTH), currentTime!!.get(Calendar.DAY_OF_MONTH))
        dialog.show()
    }

    override fun launchTimePicker() {
        val dialog = TimePickerDialog(context, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            dueDate = presenter.setTime(hourOfDay, minute)

        }, 12, 0, false)
        dialog.show()
    }

    override fun setTimePickerText(formattedHour: Int, formattedMinute: String, amOrPm: String) {
        timePicker.setText("$formattedHour:$formattedMinute $amOrPm")
    }

    override fun displayListOfGoals() {
        startActivity(context.intentFor<MainActivity>())

    }

    override fun displayEmptyTitleError() {
        context.toast("You can't have a empty title")
    }
    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context as OnFragmentInteractionListener?
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.create_gest_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.save -> {
                presenter.saveGoal(goalTitle.text.toString(), currentTime?.timeInMillis, dueDate.timeInMillis)

            }
        }
        return super.onOptionsItemSelected(item)

    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"


        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.

         * @param param1 Parameter 1.
         * *
         * @param param2 Parameter 2.
         * *
         * @return A new instance of fragment CreateGoalFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): CreateGoalFragment {
            val fragment = CreateGoalFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor

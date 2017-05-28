package com.goals.ted.goals.Fragments

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
import com.goals.ted.goals.Dates
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
class CreateGoalFragment : Fragment() {

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null
    private var currentTime: Calendar? = null
    private var dueDate: Calendar? = null
    private var mListener: OnFragmentInteractionListener? = null
    val db: MyDB by lazy{MyDB(context)}

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
        dueDate = Calendar.getInstance()

        timePickerDialog()
        datePickerDialog()
    }

    fun datePickerDialog() {
        datePicker!!.setOnClickListener { v ->
                val dialog = DatePickerDialog(v.context, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    dueDate?.set(Calendar.YEAR, year)
                    dueDate?.set(Calendar.MONTH, monthOfYear)
                    dueDate?.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    val formattedDate = DateFormatter.convertDateToDateFormat(dueDate)
                    datePicker?.setText(formattedDate)
                }, currentTime!!.get(Calendar.YEAR), currentTime!!.get(Calendar.MONTH), currentTime!!.get(Calendar.DAY_OF_MONTH))
                dialog.show()
        }

    }

    fun timePickerDialog() {
        timePicker!!.setOnClickListener { v ->
            val dialog = TimePickerDialog(v.context, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                var isAmOrPm = HOUR_AM
                val formattedMinute = StringBuilder()
                var formattedHour = hourOfDay

                dueDate?.set(Calendar.HOUR_OF_DAY, hourOfDay)
                dueDate?.set(Calendar.MINUTE, minute)
                formattedMinute.append(minute)
                if (minute == 0) {
                    formattedMinute.append(0)
                }
                if (formattedHour > 12 || formattedHour == 0) {
                    isAmOrPm = HOUR_PM
                    formattedHour = Dates().convert24To12Format(hourOfDay)
                }
                timePicker.setText("$formattedHour:$formattedMinute $isAmOrPm")
            }, 12, 0, false)
            dialog.show()
        }
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
                val titleTxt = goalTitle?.text.toString()
                if (!titleTxt.isEmpty()) {
                    db.createRecord(titleTxt, currentTime!!.timeInMillis, dueDate!!.timeInMillis)
                    startActivity(context.intentFor<MainActivity>())
                } else {
                    context.toast("You can't have a empty title")

                }
            }
        }
        return super.onOptionsItemSelected(item)

    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"
        private val HOUR_PM = "PM"
        private val HOUR_AM = "AM"


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

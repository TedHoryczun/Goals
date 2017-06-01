package com.goals.ted.goals.Fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.goals.ted.goals.Goal
import com.goals.ted.goals.MyDB
import com.goals.ted.goals.R
import com.goals.ted.goals.SubGoal
import com.goals.ted.goals.SubGoalAdapter
import kotlinx.android.synthetic.main.fragment_goal_page.*
import org.jetbrains.anko.toast

import kotlin.collections.ArrayList

class GoalPageFragment : Fragment() {

    // TODO: Rename and change types of parameters
    private var id: String? = null
    private var mParam2: String? = null

    private var adapter: SubGoalAdapter? = null
    private val myDB: MyDB by lazy{MyDB(context)}
    private lateinit var goal: Goal
    private val subGoalList: ArrayList<SubGoal> by lazy{myDB.selectSubGoalsById(id!!.toInt()) as ArrayList<SubGoal>}


    private var mListener: OnFragmentInteractionListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            id = arguments.getString(ARG_PARAM1)
            mParam2 = arguments.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_goal_page, container, false)
        setHasOptionsMenu(true)

        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        goal = myDB.selectByID(id?.toInt())!!

        dueDate.text = "Due in ${goal.daysTillDueDate()} days"
        title.text = goal.title
        subGoalRecyclerView.layoutManager = LinearLayoutManager(context)
        adapter = id?.let { SubGoalAdapter(context, this.subGoalList, it)}
        subGoalRecyclerView.adapter = adapter
        createSubGoal()
    }

    fun createSubGoal() {
            myDB.createSubGoal(goal.id, "", false)
            subGoalList.add(SubGoal(goal.id, "", false))
            adapter?.notifyItemInserted(subGoalList.size + 1)
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
        myDB.closeDb()
    }

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        fun newInstance(param1: String, param2: String): GoalPageFragment {
            val fragment = GoalPageFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }

}// Required empty public constructor

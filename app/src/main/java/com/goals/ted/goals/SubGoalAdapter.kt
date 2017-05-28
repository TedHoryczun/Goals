package com.goals.ted.goals

import android.content.Context
import android.graphics.Paint
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.ImageView
import kotlinx.android.synthetic.main.sub_goal.view.*

/**
 * Created by ted on 7/31/16.
 */
class SubGoalAdapter(private val context: Context, val subGoalList: MutableList<SubGoal>, private val goalId: String) : RecyclerView.Adapter<SubGoalAdapter.ViewHolder>() {
    private val isInEditMode = false

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBox: CheckBox = itemView.isCompleted
        val title: EditText = itemView.subGoalTitle
        val deleteButton: ImageView = itemView.deleteSubGoal
        val editSubGoalTitle: ImageView = itemView.editSubGoalTitle
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.sub_goal, null)
        val viewHolder = ViewHolder(view)

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {

            myDB = MyDB(context)
            val subGoal = subGoalList[position]
            val title = StringBuilder()
            title.replace(0, title.length, subGoal.title)
            val isChecked = subGoal.isChecked
            checkBox.isChecked = isChecked
            setTextStrike(holder, isChecked)
            if (!title.toString().isEmpty()) {

                holder.title.setText(title.toString())
            } else {
                holder.title.hint = "Enter a title"
            }
            editSubGoalHandler(holder.editSubGoalTitle, subGoal, holder)
            title.replace(0, title.length, holder.title.text.toString())
            checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                myDB?.changeCheckedSubGoal(subGoal.id, isChecked)
                setTextStrike(holder, isChecked)
            }
            deleteButton.setOnClickListener {
                myDB?.deleteRecord(MyDB.SUB_TABLE, subGoalList[position].id)
                subGoalList.removeAt(position)
                notifyItemRemoved(position)
                notifyDataSetChanged()
                println(position)
                Log.i("Arrays", subGoalList.toString())
            }
        }
    }

    private fun editSubGoalHandler(editSubGoalTitle: ImageView, subGoal: SubGoal, holder: ViewHolder) {
        editSubGoalTitle.setOnClickListener {
            val isEditMode = subGoal.isEditMode
            determineEditModeAndSetIt(subGoal, holder, isEditMode)
        }
    }

    fun determineEditModeAndSetIt(subGoal: SubGoal, holder: ViewHolder, isEditMode: Boolean) {
        if (isEditMode) {
            subGoal.isEditMode = false
            turnOnOffEditMode(false, holder, subGoal)
        } else {
            subGoal.isEditMode = true
            turnOnOffEditMode(true, holder, subGoal)
        }
    }

    private fun turnOnOffEditMode(isInEditMode: Boolean, holder: ViewHolder, subGoal: SubGoal) {
        with(holder) {
            if (isInEditMode) {
                editSubGoalTitle.setImageResource(R.drawable.ic_add_black_24dp)
                title.isEnabled = true
            } else {
                editSubGoalTitle.setImageResource(R.drawable.ic_mode_edit_black_24dp)
                title.isEnabled = false
                myDB?.subGoalUpdate(MyDB.SUB_TITLE, subGoal.id, title.text.toString())
            }
        }

    }

    fun setTextStrike(holder: ViewHolder, isChecked: Boolean) {
        with(holder) {
            if (isChecked) {

                title.paintFlags = title.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                title.paintFlags = title.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()

            }
        }
    }


    override fun getItemCount(): Int {
        return subGoalList.size
    }

    companion object {
        private var myDB: MyDB? = null

//        fun onSave() {
//
//            for (subGoal in subGoalList!!) {
//
//                myDB!!.subGoalUpdate(MyDB.SUB_TITLE, subGoal.id, subGoal.title)
//                println(subGoal.title)
//            }
//        }
    }


}

package com.goals.ted.goals

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView

import com.goals.ted.goals.Activities.GoalPage
import kotlinx.android.synthetic.main.fragment_goal_page.view.*
import kotlinx.android.synthetic.main.item.view.*
import org.jetbrains.anko.AlertDialogBuilder
import org.jetbrains.anko.alert
import org.jetbrains.anko.intentFor

/**
 * Created by ted on 7/20/16.
 */
class GoalAdapter(private val context: Context, private val goalList: MutableList<Goal>) : RecyclerView.Adapter<GoalAdapter.ViewHolder>() {
    private val db: MyDB by lazy { MyDB(context) }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.goalTitle
        val dueDate: TextView = itemView.goaldueDate
        val checks: TextView = itemView.checks
        val progressBar: ProgressBar = itemView.progressBar
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        val viewHolder = ViewHolder(view)

        view.setOnClickListener {
            val currentGoal = goalList[viewHolder.adapterPosition]
            context.startActivity(context.intentFor<GoalPage>("id" to currentGoal.id))
        }
        view.setOnLongClickListener {
            displayDeleteDialog(viewHolder.adapterPosition)
            true
        }
        return viewHolder
    }

    private fun displayDeleteDialog(position: Int) {
        context.alert("Delete") {
            this.positiveButton("Delete") { deleteGoal(position) }
            this.negativeButton("Cancel") { this.cancel() }
            message("Would you like to delete this Goal?")
            cancellable(true)
        }.show()
    }

    private fun deleteGoal(position: Int) {
        val currentGoal = goalList[position]
        db.deleteRecord(MyDB.EMP_TABLE, currentGoal.id)
        goalList.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentGoal = goalList[position]
        val subGoals = db.selectSubGoalsById(currentGoal.id)
        val subGoalSize = subGoals.size
        var howManyChecked = 0
        subGoals.filter { it.isChecked == true }
                .forEach { howManyChecked += 1 }

        with(holder) {

            val goalTitle = currentGoal.title
            val isTitleTooLong = isGoalTitleTooLong(goalTitle)
            if (isTitleTooLong) {
                val shrunkenTitle = shrinkAndEditTitle(goalTitle)
                title.text = shrunkenTitle
            } else {
                title.text = goalTitle
            }
            val daysTillDueDate: String = currentGoal.daysTillDueDate().toString()
            dueDate.text = "Due in $daysTillDueDate days"
            checks.text = "$howManyChecked/$subGoalSize"
            progressBar.progress = currentGoal.percentageComplete()
        }
    }

    fun isGoalTitleTooLong(title: String): Boolean {
        val MAX_STRING_CHAR_WIDTH = 12
        return title.length >= MAX_STRING_CHAR_WIDTH
    }

    private fun shrinkAndEditTitle(goalTitle: String): String {
        val shunkAndEditedTitle: String
        val stringBuilder = shrinkTitleToMax(goalTitle)
        stringBuilder.append("..")
        shunkAndEditedTitle = stringBuilder.toString()
        return shunkAndEditedTitle
    }

    private fun shrinkTitleToMax(goalTitle: String): StringBuilder {
        val stringBuilder = StringBuilder()
        for (i in 0..MAX_TITLE_WIDTH - 1) {
            val selectedChar = goalTitle[i]
            stringBuilder.append(selectedChar)
        }
        return stringBuilder

    }

    override fun getItemCount(): Int {
        return goalList.size
    }

    companion object {

        private val MAX_TITLE_WIDTH = 11
    }
}

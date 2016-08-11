package com.goals.ted.goals;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.goals.ted.goals.Activities.GoalPage;

import java.util.List;

/**
 * Created by ted on 7/20/16.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<Goal> goalList;
    private Context context;
    private MyDB db;

    public RecyclerAdapter(Context context, List<Goal> goalList) {
        this.goalList = goalList;
        this.context = context;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView dueDate;
        private TextView percentComplete;
        private TextView checks;

        public ViewHolder(View itemView) {
            super(itemView);
            checks = (TextView) itemView.findViewById(R.id.checks);
            title = (TextView) itemView.findViewById(R.id.goalTitle);
            dueDate = (TextView) itemView.findViewById(R.id.goaldueDate);
            percentComplete = (TextView) itemView.findViewById(R.id.percentComplete);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        db = new MyDB(context);
        final ViewHolder viewHolder = new ViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                Goal currentGoal = goalList.get(position);
                Intent intent = new Intent(context, GoalPage.class);
                intent.putExtra("id", currentGoal.getId());
                context.startActivity(intent);
            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("Delete")
                        .setMessage("Would you like to delete this Goal")
                        .setCancelable(true)
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int position = viewHolder.getAdapterPosition();
                                Goal currentGoal = goalList.get(position);
                                db.deleteRecord(currentGoal.getId());
                                goalList.remove(position);
                                notifyItemRemoved(position);
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = alertDialog.create();
                alert.show();

                return true;
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Goal currentGoal = goalList.get(position);
        List<SubGoal> subGoals = db.selectSubGoals(currentGoal.getId());
        int subGoalSize = subGoals.size();
        int howManyChecked = 0;
        for (SubGoal i : subGoals) {
            if (i.isChecked() == true) {
                howManyChecked += 1;
            }
        }

        holder.title.setText(currentGoal.getTitle());
        holder.dueDate.setText("Due in " + String.valueOf(currentGoal.daysTillDueDate() + " days"));
        holder.percentComplete.setText(String.valueOf(currentGoal.percentageComplete() + "%"));
        holder.checks.setText(howManyChecked + "/" + subGoalSize);


    }

    @Override
    public int getItemCount() {
        return goalList.size();
    }


}

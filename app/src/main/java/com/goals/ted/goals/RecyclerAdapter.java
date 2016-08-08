package com.goals.ted.goals;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.goals.ted.goals.Activities.GoalPage;

import java.util.List;

/**
 * Created by ted on 7/20/16.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{
    private List<Goal> goalList;
    private Context context;
    private MyDB db;
    public RecyclerAdapter(Context context, List<Goal> goalList){
        this.goalList = goalList;
        this.context = context;

    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private TextView dueDate;
        private TextView percentComplete;
       public ViewHolder(View itemView){
           super(itemView);
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
                int position = viewHolder.getAdapterPosition();
                Goal currentGoal = goalList.get(position);
                Log.i("goal id: ", String.valueOf(currentGoal.getId()));
                db.deleteRecord(currentGoal.getId());
                goalList.remove(position);
                notifyItemRemoved(position);
                return true;
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Goal currentGoal = goalList.get(position);
        holder.title.setText(currentGoal.getTitle());
        holder.dueDate.setText("Due in " + String.valueOf(currentGoal.daysTillDueDate() + " days"));
        holder.percentComplete.setText(String.valueOf(currentGoal.percentageComplete() + "%"));



    }

    @Override
    public int getItemCount() {
        return goalList.size();
    }


}

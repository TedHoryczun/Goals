package com.goals.ted.goals;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by ted on 7/20/16.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{
    private List<Goal> goalList;
    private Context context;
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
        final MyDB db = new MyDB(context);
        final ViewHolder viewHolder = new ViewHolder(view);
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final int position = viewHolder.getAdapterPosition();
                Goal currentGoal = goalList.get(position);
                Log.i("goal id: ", String.valueOf(currentGoal.getId()));
                db.deleteRecord(currentGoal.getId());
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

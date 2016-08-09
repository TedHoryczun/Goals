package com.goals.ted.goals;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by ted on 7/31/16.
 */
public class SubGoalAdapter extends RecyclerView.Adapter<SubGoalAdapter.ViewHolder>{
    private List<SubGoal> subGoalList;
    private Context context;

    public SubGoalAdapter(Context context, List<SubGoal> subGoalList){
        this.context = context;
        this.subGoalList = subGoalList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CheckBox checkBox;
        private EditText title;
        private ImageView deleteButton;
        public ViewHolder(View itemView) {
            super(itemView);
            checkBox = (CheckBox) itemView.findViewById(R.id.isCompleted);
            title = (EditText) itemView.findViewById(R.id.subGoalTitle);
            deleteButton = (ImageView) itemView.findViewById(R.id.deleteSubGoal);
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sub_goal, null);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final MyDB myDB = new MyDB(context);
        final SubGoal subGoal = subGoalList.get(position);
        String title = subGoal.getTitle();
        boolean isChecked = subGoal.isChecked();
        holder.checkBox.setChecked(isChecked);
        setTextStrike(holder, isChecked);
        if(title != null){

            holder.title.setText(title);
        }else{
            holder.title.setHint("Enter a title");
        }
        holder.title.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                System.out.println("changed!!");
            }
        });
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                myDB.changeCheckedSubGoal(subGoal.getId(), isChecked);
                setTextStrike(holder, isChecked);
            }
        });
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDB.deleteSubGoal(subGoal.getId());
                subGoalList.remove(position);
                notifyItemRemoved(position);

            }
        });
    }
    public void setTextStrike(ViewHolder holder, boolean isChecked){
        if(isChecked==true){
            holder.title.setPaintFlags(holder.title.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }else{
            holder.title.setPaintFlags( holder.title.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));

        }
    }


    @Override
    public int getItemCount() {
        return subGoalList.size();
    }


}
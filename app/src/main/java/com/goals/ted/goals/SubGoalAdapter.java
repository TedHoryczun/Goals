package com.goals.ted.goals;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by ted on 7/31/16.
 */
public class SubGoalAdapter extends RecyclerView.Adapter<SubGoalAdapter.ViewHolder> {
    private static List<SubGoal> subGoalList;
    private Context context;
    private StringBuilder title;
    private String goalId;
    private boolean isInEditMode = false;

    private static MyDB myDB;

    public SubGoalAdapter(Context context, List<SubGoal> subGoalList, String goalId) {
        this.context = context;
        this.subGoalList = subGoalList;
        this.goalId = goalId;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox checkBox;
        private EditText title;
        private ImageView deleteButton;
        private ImageView editSubGoalTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            checkBox = (CheckBox) itemView.findViewById(R.id.isCompleted);
            title = (EditText) itemView.findViewById(R.id.subGoalTitle);
            deleteButton = (ImageView) itemView.findViewById(R.id.deleteSubGoal);
            editSubGoalTitle = (ImageView) itemView.findViewById(R.id.editSubGoalTitle);
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
        myDB = new MyDB(context);
        final SubGoal subGoal = subGoalList.get(position);
        title = new StringBuilder();
        title.replace(0, title.length(), subGoal.getTitle());
        boolean isChecked = subGoal.isChecked();
        holder.checkBox.setChecked(isChecked);
        setTextStrike(holder, isChecked);
        if (!title.toString().isEmpty()) {

            holder.title.setText(title.toString());
        } else {
            holder.title.setHint("Enter a title");
        }
        editSubGoalHandler(holder.editSubGoalTitle, subGoal, holder);
        title.replace(0, title.length(), holder.title.getText().toString());
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
                myDB.deleteRecord(MyDB.SUB_TABLE, subGoalList.get(position).getId());
                subGoalList.remove(position);
                notifyItemRemoved(position);
                notifyDataSetChanged();
                System.out.println(position);
                Log.i("Arrays", subGoalList.toString());

            }
        });
    }

    private void editSubGoalHandler(ImageView editSubGoalTitle, final SubGoal subGoal, final ViewHolder holder) {
        editSubGoalTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isEditMode = subGoal.isEditMode();
                determineEditModeAndSetIt(subGoal, holder, isEditMode);
            }
        });
    }

    public void determineEditModeAndSetIt(SubGoal subGoal, final ViewHolder holder, boolean isEditMode) {
        if (isEditMode) {
            subGoal.setEditMode(false);
            turnOnOffEditMode(false, holder, subGoal);
        } else {
            subGoal.setEditMode(true);
            turnOnOffEditMode(true, holder, subGoal);
        }
    }

    private void turnOnOffEditMode(boolean isInEditMode, ViewHolder holder, SubGoal subGoal) {
        if (isInEditMode) {
            holder.editSubGoalTitle.setImageResource(R.drawable.ic_add_black_24dp);
            holder.title.setEnabled(true);
        } else {
            holder.editSubGoalTitle.setImageResource(R.drawable.ic_mode_edit_black_24dp);
            holder.title.setEnabled(false);
            myDB.subGoalUpdate(MyDB.SUB_TITLE, subGoal.getId(), holder.title.getText().toString());
        }
        System.out.println("clicked");

    }

    public void setTextStrike(ViewHolder holder, boolean isChecked) {
        if (isChecked == true) {
            holder.title.setPaintFlags(holder.title.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.title.setPaintFlags(holder.title.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));

        }
    }

    public static void onSave() {

        for (SubGoal subGoal : subGoalList) {

            myDB.subGoalUpdate(MyDB.SUB_TITLE, subGoal.getId(), subGoal.getTitle());
            System.out.println(subGoal.getTitle());
        }
    }


    @Override
    public int getItemCount() {
        return subGoalList.size();
    }


}

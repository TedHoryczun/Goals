import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.goals.ted.goals.R;
import com.goals.ted.goals.SubGoal;

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
        ViewHolder viewHolder = new ViewHolder(parent);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SubGoal subGoal = subGoalList.get(position);
        holder.title.setText(subGoal.getTitle());
        holder.checkBox.setChecked(subGoal.isChecked());

    }

    @Override
    public int getItemCount() {
        subGoalList.size();
    }


}

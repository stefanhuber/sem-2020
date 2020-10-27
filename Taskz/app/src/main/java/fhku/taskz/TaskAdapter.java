package fhku.taskz;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    protected List<Task> tasks;

    public TaskAdapter(TaskDao dao) {
        tasks = dao.queryAllTasks();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        public TextView taskTitle;
        public TextView taskInfo;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            if (itemView instanceof ViewGroup) {
                taskTitle = itemView.findViewById(R.id.task_title);
                taskInfo = itemView.findViewById(R.id.task_info);
            }
        }
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout ll = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new TaskViewHolder(ll);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.taskTitle.setText(tasks.get(position).title);
        holder.taskInfo.setText(tasks.get(position).description);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }



}

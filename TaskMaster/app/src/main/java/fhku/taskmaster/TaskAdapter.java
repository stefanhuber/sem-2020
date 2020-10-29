package fhku.taskmaster;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    protected TaskDao dao;
    protected List<Task> tasks;

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        public TextView taskTitle;
        public TextView taskDueDate;
        public ImageView taskPriority;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            taskTitle = itemView.findViewById(R.id.task_title);
            taskDueDate = itemView.findViewById(R.id.task_date);
            taskPriority = itemView.findViewById(R.id.task_priority);
        }
    }

    public TaskAdapter(TaskDao dao) {
        this.dao = dao;
        loadTasks();
    }

    public void loadTasks() {
        tasks = dao.getAllTasks();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View taskItem = inflater.inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(taskItem);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.taskTitle.setText(task.title);
        holder.taskDueDate.setText(task.getFormatedDate());

        if (task.priority == 2) {
            holder.taskPriority.setImageResource(R.drawable.priority_medium);
        } else if (task.priority == 1) {
            holder.taskPriority.setImageResource(R.drawable.priority_high);
        }
    }

    @Override
    public int getItemCount() {
        return tasks == null ? 0 : tasks.size();
    }


}

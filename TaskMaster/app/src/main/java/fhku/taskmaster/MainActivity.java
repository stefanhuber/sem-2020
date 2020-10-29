package fhku.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppDatabase database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "task_db")
                .allowMainThreadQueries()
                .build();

        if (database.getTaskDao().getAllTasks().size() == 0) {
            for (int i = 0; i < 20; i++) {
                Task t = new Task();
                t.title = "Task title " + i;
                t.dueDate = Calendar.getInstance().getTime();
                t.priority = (int) Math.ceil(Math.random() * 3);
                database.getTaskDao().addTask(t);
            }
        }

        RecyclerView taskList = findViewById(R.id.task_list);
        taskList.setLayoutManager(new LinearLayoutManager(this));
        taskList.setAdapter(new TaskAdapter(database.getTaskDao()));
    }
}
package fhku.taskz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    protected AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appDatabase = Room.databaseBuilder(this, AppDatabase.class, "task_db")
                        .allowMainThreadQueries()
                        .build();

        TaskDao dao = appDatabase.getTaskDao();
        if (dao.queryAllTasks().size() == 0) {
            for (int i = 1; i <= 200; i++) {
                Task t = new Task();
                t.title = "Task " + i;
                t.description = "Task description bla bla " + i;
                dao.insertTask(t);
            }
        }

        RecyclerView list = findViewById(R.id.task_list);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(new TaskAdapter(appDatabase.getTaskDao()));
        list.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }
}
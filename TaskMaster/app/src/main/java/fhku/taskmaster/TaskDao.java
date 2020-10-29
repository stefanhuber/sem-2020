package fhku.taskmaster;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM Task")
    public List<Task> getAllTasks();

    @Insert
    public void addTask(Task task);

}

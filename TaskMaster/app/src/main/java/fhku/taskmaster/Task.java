package fhku.taskmaster;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.DateFormat;
import java.util.Date;

@Entity
public class Task {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String title;
    public int priority;
    public Date dueDate;

    public String getFormatedDate() {
        DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT);

        if (dueDate != null) {
            return format.format(dueDate);
        } else {
            return "";
        }
    }
}

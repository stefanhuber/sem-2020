package fhku.geotracker;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Track {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String title;
    public String description;
    public Date start;
    public Date end;

}

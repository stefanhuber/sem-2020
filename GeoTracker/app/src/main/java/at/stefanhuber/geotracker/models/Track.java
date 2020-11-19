package at.stefanhuber.geotracker.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

@Entity
public class Track {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String name;
    public String description;
    public Date started;
    public Date stopped;

    public String formattedDuration() {
        if (stopped != null && started != null) {
            long time = stopped.getTime() - started.getTime();
            long diff = 0;

            long hours = time / 1000 / 60 / 60;
            diff = time - (hours * 1000 * 60 * 60);

            long minutes = diff / 1000 * 60;

            return hours + "h " + minutes + "m";
        } else {
            return "";
        }
    }

    public Track stop() {
        stopped = Calendar.getInstance().getTime();
        return this;
    }

    public static Track fromNow() {
        Date now = Calendar.getInstance().getTime();
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);

        Track track = new Track();
        track.started = now;
        track.name = df.format(now);
        return track;
    }

}

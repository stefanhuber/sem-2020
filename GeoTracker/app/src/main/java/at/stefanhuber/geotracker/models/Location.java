package at.stefanhuber.geotracker.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Location {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public double latitude;
    public double longitude;
    public double altidude;
    public Date timestamp;

    public long trackId;

    public void setTimestamp(long timestamp) {
        this.timestamp = new Date(timestamp);
    }

    public static Location from(android.location.Location location, Track track) {
        Location l = new Location();
        l.latitude = location.getLatitude();
        l.longitude = location.getLongitude();
        l.altidude = location.getAltitude();
        l.setTimestamp(location.getTime());
        l.trackId = track.id;
        return l;
    }
}

package at.stefanhuber.geotracker.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import at.stefanhuber.geotracker.models.Location;
import at.stefanhuber.geotracker.models.Track;

@Dao
public interface TrackDao {

    @Insert
    public long createTrack(Track track);

    @Update
    public void updateTrack(Track track);

    @Insert
    public long createLocation(Location location);

    @Query("SELECT * FROM Track")
    public List<Track> listTracks();

    @Query("SELECT * FROM Track WHERE id = :id")
    public Track getTrack(long id);

}

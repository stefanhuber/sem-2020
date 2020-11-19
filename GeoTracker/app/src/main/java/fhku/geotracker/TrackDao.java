package fhku.geotracker;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TrackDao {

    @Query("SELECT * FROM Track")
    public List<Track> getTracks();

}

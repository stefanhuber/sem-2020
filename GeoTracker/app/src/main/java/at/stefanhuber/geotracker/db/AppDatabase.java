package at.stefanhuber.geotracker.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import at.stefanhuber.geotracker.models.Location;
import at.stefanhuber.geotracker.models.Track;

@Database(entities = {Location.class, Track.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract TrackDao getTrackDao();

}

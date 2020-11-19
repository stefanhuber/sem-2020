package at.stefanhuber.geotracker.db;

import android.content.Context;

import androidx.room.Room;

public class DatabaseUtils {

    public static AppDatabase getDatabase(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "tracks")
            .allowMainThreadQueries()
            .build();
    }

}

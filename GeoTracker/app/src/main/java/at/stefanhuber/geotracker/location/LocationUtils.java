package at.stefanhuber.geotracker.location;

import com.google.android.gms.location.LocationRequest;

public class LocationUtils {

    public static LocationRequest createLocationRequest() {
        LocationRequest lr = LocationRequest.create();
        lr.setInterval(5000);
        lr.setSmallestDisplacement(8);
        lr.setMaxWaitTime(60000);
        lr.setFastestInterval(3000);
        lr.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return lr;
    }

}

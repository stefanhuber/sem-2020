package at.stefanhuber.geotracker.services;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.os.Looper;


import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import at.stefanhuber.geotracker.db.DatabaseUtils;
import at.stefanhuber.geotracker.db.TrackDao;
import at.stefanhuber.geotracker.location.LocationUtils;
import at.stefanhuber.geotracker.R;
import at.stefanhuber.geotracker.models.Location;
import at.stefanhuber.geotracker.models.Track;

public class LocationTrackingService extends Service {

    public static final String TAG = "LOCATION_TRACKING";
    public static final String CHANNEL = "gps-tracker-channel-01";
    public static final int SERVICE = 123456;

    public static boolean recording = false;

    protected FusedLocationProviderClient locationClient;
    protected LocationCallback locationCallback;

    protected Track currentTrack = null;
    protected TrackDao trackDao;

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        locationClient = LocationServices.getFusedLocationProviderClient(this);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                onLocationUpdate(locationResult.getLastLocation());
            }
        };
        trackDao = DatabaseUtils.getDatabase(this.getApplicationContext()).getTrackDao();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onLocationUpdate(android.location.Location location) {
        if (currentTrack != null) {
            recording = true;
            Location l = Location.from(location, currentTrack);
            trackDao.createLocation(l);
        }
    }

    public void createTrack() {
        if (currentTrack == null) {
            Track track = Track.fromNow();
            track.id = trackDao.createTrack(track);
            currentTrack = track;
        }
    }

    public void stopTrack() {
        if (currentTrack != null) {
            currentTrack.stop();
            trackDao.updateTrack(currentTrack);
            currentTrack = null;
        }
    }

    public void startLocationUpdates() {
        LocationRequest lr = LocationUtils.createLocationRequest();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            createTrack();
            locationClient.requestLocationUpdates(lr, locationCallback, Looper.myLooper());
        }
    }

    public void stopLocationUpdates(boolean pause) {
        locationClient.removeLocationUpdates(locationCallback);
        recording = false;

        if (!pause) {
            stopTrack();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();

        if (action != null && action.equals("START") && !recording) {
            startForeground(SERVICE, createNotification(true));
            startLocationUpdates();
        } else if (action != null && action.equals("CONTINUE") && !recording) {
            startLocationUpdates();
            NotificationManagerCompat.from(this).notify(SERVICE, createNotification(true));
        } else if (action != null && action.equals("PAUSE") && recording) {
            stopLocationUpdates(true);
            NotificationManagerCompat.from(this).notify(SERVICE, createNotification(false));
        } else if (action != null && action.equals("STOP")) {
            stopLocationUpdates(false);
            stopSelf();
        }

        return START_NOT_STICKY;
    }

    public PendingIntent getPendingIntent(String action) {
        Intent intent = new Intent(this, LocationTrackingService.class);
        intent.setAction(action);

        return PendingIntent.getService(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public Notification createNotification(boolean pause) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL)
                .setSmallIcon(R.drawable.ic_navigate_outline)
                .setContentTitle(getString(R.string.notification_tracking_title))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        if (pause) {
            builder.addAction(
                    R.drawable.ic_pause_outline,
                    getString(R.string.notification_tracking_pause),
                    getPendingIntent("PAUSE"));
        } else {
            builder.addAction(
                    R.drawable.ic_navigate_outline,
                    getString(R.string.notification_tracking_continue),
                    getPendingIntent("CONTINUE"));
        }

        builder.addAction(
                R.drawable.ic_stop_outline,
                getString(R.string.notification_tracking_stop),
                getPendingIntent("STOP"));

        return builder.build();
    }

    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL,
                    getString(R.string.app_name),
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(getString(R.string.app_description));
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopLocationUpdates(false);
    }
}

package fhku.backgroundmedia;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.io.File;

public class MediaService extends Service {
    protected String LOG_TAG = "Background Media";
    protected String CHANNEL = "music-player-channel";
    protected MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();

            try {
                File recording = new File(getFilesDir(), "recording.mp3");
                mediaPlayer.setDataSource(recording.getAbsolutePath());
                mediaPlayer.prepare();
                mediaPlayer.setLooping(true);
            } catch (Exception e) {
            }

            createNotificationChannel();
            startForeground(666, createNotification(true));
        }

        if (intent.getAction() == "PLAY") {
            mediaPlayer.start();
            Log.i(LOG_TAG, "Service Play action");
            NotificationManagerCompat.from(this).notify(666, createNotification(false));
        } else if (intent.getAction() == "PAUSE") {
            Log.i(LOG_TAG, "Service Pause action");
            mediaPlayer.pause();
            NotificationManagerCompat.from(this).notify(666, createNotification(true));
        } else if (intent.getAction() == "DELETE") {
            Log.i(LOG_TAG, "Service Delete action");
            mediaPlayer.stop();
            mediaPlayer = null;
            File recording = new File(getFilesDir(), "recording.mp3");
            recording.delete();
            startActivity(new Intent(this, MainActivity.class));
            stopSelf();
        }

        return START_NOT_STICKY;
    }

    public PendingIntent getPendingIntent(String action) {
        Intent intent = new Intent(this, MediaService.class);
        intent.setAction(action);

        return PendingIntent.getService(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public Notification createNotification(boolean play) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL)
                .setSmallIcon(R.drawable.ic_musical_notes_outline)
                .setContentTitle("Music Player")
                .setContentText("The service is playing your recording in the background")
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        if (play) {
            builder.addAction(R.drawable.ic_play_outline, "Play", getPendingIntent("PLAY"));
        } else {
            builder.addAction(R.drawable.ic_pause_outline, "Pause", getPendingIntent("PAUSE"));
        }

        builder.addAction(R.drawable.ic_trash_outline, "Delete", getPendingIntent("DELETE"));
        //NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        //notificationManager.notify(666, builder.build());
        return builder.build();
    }

    public void createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL, "Music Player", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Background Music Player Channel");
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.createNotificationChannel(channel);
        }
    }
}

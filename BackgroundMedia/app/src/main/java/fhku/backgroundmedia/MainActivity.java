package fhku.backgroundmedia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    protected int PERMISSION_CODE = 1234;

    protected String LOG_TAG = "Background Media";

    protected File recording;
    protected MediaRecorder mediaRecorder;

    protected Button play;
    protected Button record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play = findViewById(R.id.play);
        record = findViewById(R.id.record);
        recording = new File(getFilesDir(), "recording.mp3");
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!recording.isFile()) {
            play.setEnabled(false);
        }
        if (recording.isFile()) {
            record.setEnabled(false);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        if (!recording.isFile()) {
            play.setEnabled(false);
        }
        if (recording.isFile()) {
            record.setEnabled(false);
        }
    }

    public void clickRecord(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[] {Manifest.permission.RECORD_AUDIO},
                    PERMISSION_CODE
            );
        } else {
            Button button = (Button) view;

            if (button.getText().equals("Start Recording")) {
                play.setEnabled(false);
                button.setText("Stop Recording");
                startRecording();
            } else {
                stopRecording();
                button.setText("Start Recording");
                play.setEnabled(true);
                record.setEnabled(false);
            }
        }
    }

    public void clickPlay(View view) {
        Intent serviceIntent = new Intent(this, MediaService.class);
        serviceIntent.setAction("PLAY");
        startService(serviceIntent);
    }

    public void prepareMediaRecorder() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediaRecorder.setOutputFile(recording.getAbsolutePath());
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
    }

    public void startRecording() {
        if (mediaRecorder == null) {
            prepareMediaRecorder();
        }

        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, e.getMessage());
        }
    }

    public void stopRecording() {
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_CODE &&
            permissions.length > 0 &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            clickRecord(record);
        }
    }
}
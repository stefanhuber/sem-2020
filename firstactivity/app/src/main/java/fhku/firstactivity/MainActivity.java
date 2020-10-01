package fhku.firstactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    public final String TAG = "DEMO_PROJECT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "activity created");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "activity resumed");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "activity restarted");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "activity started");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "activity stopped");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "activity paused");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "activity destroyed");
    }

}
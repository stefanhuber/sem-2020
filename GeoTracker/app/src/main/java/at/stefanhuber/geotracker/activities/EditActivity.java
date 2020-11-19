package at.stefanhuber.geotracker.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.textfield.TextInputEditText;

import at.stefanhuber.geotracker.R;
import at.stefanhuber.geotracker.db.DatabaseUtils;
import at.stefanhuber.geotracker.db.TrackDao;
import at.stefanhuber.geotracker.models.Track;

public class EditActivity extends AppCompatActivity {

    protected TextInputEditText title;
    protected TextInputEditText description;
    protected TrackDao trackDao;
    protected Track track;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        title = findViewById(R.id.tracker_edit_title);
        description = findViewById(R.id.tracker_edit_description);
        trackDao = DatabaseUtils.getDatabase(this.getApplicationContext()).getTrackDao();

        Intent intent = getIntent();
        if (intent.hasExtra("track")) {
            track = trackDao.getTrack(intent.getLongExtra("track", 0));
            title.setText(track.name);
            description.setText(track.description);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.track_edit) {
            track.name = title.getText().toString();
            track.description = description.getText().toString();
            trackDao.updateTrack(track);

            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
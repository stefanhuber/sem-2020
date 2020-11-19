package at.stefanhuber.geotracker.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

import org.osmdroid.config.Configuration;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;

import at.stefanhuber.geotracker.adapters.TrackAdapter;
import at.stefanhuber.geotracker.db.DatabaseUtils;
import at.stefanhuber.geotracker.db.TrackDao;
import at.stefanhuber.geotracker.location.LocationUtils;
import at.stefanhuber.geotracker.mapping.OpenTopoMapTileSource;
import at.stefanhuber.geotracker.R;
import at.stefanhuber.geotracker.models.Track;
import at.stefanhuber.geotracker.services.LocationTrackingService;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final int PERMISSION_LOCATION_REQUEST = 11;
    public static final int SETTINGS_LOCATION_REQUEST = 12;

    protected TrackDao trackDao;
    protected ActionBarDrawerToggle toggle;
    protected DrawerLayout drawer;
    protected Toolbar toolbar;
    protected NavigationView navigationView;
    protected MapView map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDrawer();

        trackDao = DatabaseUtils.getDatabase(this.getApplicationContext()).getTrackDao();
        TrackAdapter trackAdapter = new TrackAdapter(trackDao);
        trackAdapter.setOnTrackEditListener((Track track) -> {
            Intent intent = new Intent(this, EditActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("track", track.id);
            startActivity(intent);
        });
        trackAdapter.setOnTrackMapListener((Track track) -> {

        });

        RecyclerView list = findViewById(R.id.track_list);
        list.setAdapter(trackAdapter);
        list.setLayoutManager(new LinearLayoutManager(this));

    }
/*
    public void initMap() {
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        map = findViewById(R.id.map);
        map.setTileSource(new OpenTopoMapTileSource());
        map.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.NEVER);
        map.setMultiTouchControls(true);
        map.getController().setZoom(5d);
    }
*/
    public void initDrawer() {
        navigationView = findViewById(R.id.nav_view);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.app_name, R.string.app_name);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void onResume() {
        super.onResume();
        //map.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //map.onPause();
    }

    public void onClick(View view) {
        startLocationTracking();
    }

    public void startLocationTracking() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_LOCATION_REQUEST);
        } else {
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(LocationUtils.createLocationRequest());

            SettingsClient settingsClient = LocationServices.getSettingsClient(this);
            Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(builder.build());

            task.addOnSuccessListener(this, (LocationSettingsResponse locationSettingsResponse) -> {
                Intent intent = new Intent(this, LocationTrackingService.class);
                intent.setAction("START");
                startService(intent);
            });
            task.addOnFailureListener(this, (Exception e) -> {
                if (e instanceof ResolvableApiException) {
                    ResolvableApiException resolvable = (ResolvableApiException) e;
                    try {
                        resolvable.startResolutionForResult(this, SETTINGS_LOCATION_REQUEST);
                    } catch (IntentSender.SendIntentException sendIntentException) {
                    }
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_LOCATION_REQUEST &&
            permissions.length > 1 &&
            permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startLocationTracking();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SETTINGS_LOCATION_REQUEST && resultCode == RESULT_OK) {
            startLocationTracking();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
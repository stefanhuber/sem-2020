package fhku.backgroundwork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.work.Constraints;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.Manifest;
import android.app.DownloadManager;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... urls) {
            return loadImageFromNetwork(urls[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }
    }

    protected static final String TAG = "BACKGROUND_WORK";
    protected String url = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/dd/Full_Moon_Luc_Viatour.jpg/1024px-Full_Moon_Luc_Viatour.jpg";
    protected ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.image_1);

        findViewById(R.id.start_0).setOnClickListener(this);
        findViewById(R.id.start_1).setOnClickListener(this);
        findViewById(R.id.start_2).setOnClickListener(this);
        findViewById(R.id.start_3).setOnClickListener(this);
        findViewById(R.id.start_4).setOnClickListener(this);
        findViewById(R.id.start_5).setOnClickListener(this);
    }

    public Bitmap loadImageFromNetwork(String url) {
        try {
            RequestQueue queue = Volley.newRequestQueue(this);
            RequestFuture<Bitmap> future = RequestFuture.newFuture();

            ImageRequest imageRequest = new ImageRequest(
                    url,
                    future,
                    512,
                    512,
                    ImageView.ScaleType.FIT_CENTER,
                    Bitmap.Config.ARGB_8888,
                    future);
            queue.add(imageRequest);
            return future.get(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return null;
        }
    }

    public void createANR() {
        int i = 1;
        while (true) {
            Log.i(TAG, i + " iteration");
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
            }
            i++;
        }
    }

    public void loadInThread1() {
        imageView.setImageBitmap(null);

        new Thread(() -> {
            Bitmap bitmap = loadImageFromNetwork(url);
            imageView.setImageBitmap(bitmap);
        }).start();
    }

    public void loadInThread2() {
        imageView.setImageBitmap(null);

        new Thread(() -> {
            Bitmap bitmap = loadImageFromNetwork(url);
            imageView.post(() -> {
                imageView.setImageBitmap(bitmap);
            });
        }).start();
    }

    public void loadInAsyncTask() {
        imageView.setImageBitmap(null);
        new DownloadImageTask().execute(url);
    }

    public void downloadLargeFile() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
                123
            );
            return;
        }

        Uri uri = Uri.parse("https://upload.wikimedia.org/wikipedia/commons/f/f6/Videoonwikipedia.ogv");
        DownloadManager.Request request = new DownloadManager.Request(uri)
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                .setTitle("Large video")
                .setDescription("Sensless but large video")
                .setAllowedOverRoaming(false)
                .setVisibleInDownloadsUi(true)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "video.ogv");

        DownloadManager dmgr = getSystemService(DownloadManager.class);
        dmgr.enqueue(request);
    }

    public void enqueueWorker() {
        Constraints constraints = new Constraints.Builder()
            .setRequiresCharging(true)
            .setRequiresBatteryNotLow(true)
            .build();

        PeriodicWorkRequest request = new PeriodicWorkRequest.Builder(
            DemoWorker.class, 15, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build();

        WorkManager.getInstance(this)
            .enqueue(request);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 123 &&
            grantResults.length > 0 &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            downloadLargeFile();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.start_0) {
            createANR();
        } else if (v.getId() == R.id.start_1) {
            loadInThread1();
        } else if (v.getId() == R.id.start_2) {
            loadInThread2();
        } else if (v.getId() == R.id.start_3) {
            loadInAsyncTask();
        } else if (v.getId() == R.id.start_4) {
            downloadLargeFile();
        } else if (v.getId() == R.id.start_5) {
            enqueueWorker();
        }
    }
}
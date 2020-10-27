package fhku.captureimage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView image = findViewById(R.id.image);

        Intent intent = getIntent();
        if (intent.hasExtra("image_saved")) {
            image.post(() -> {
                try {
                    File picture = new File(getFilesDir(), "my_picture.jpg");
                    Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(picture));
                    image.setImageBitmap(bitmap);
                } catch (Exception e) {
                    Log.e("TAG", e.getMessage());
                }
            });
        }
    }

    public void openCamera(View view) {
        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);
    }
}
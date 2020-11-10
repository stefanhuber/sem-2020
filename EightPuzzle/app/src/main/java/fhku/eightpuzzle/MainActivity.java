package fhku.eightpuzzle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;

public class MainActivity extends AppCompatActivity {

    protected static final String TAG = "MAIN";

    protected ImageView image4;
    protected ImageView image5;
    protected ImageView image6;
    protected ImageView[] images = new ImageView[8];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        images[0] = findViewById(R.id.image_1);
        images[1] = findViewById(R.id.image_2);
        images[2] = findViewById(R.id.image_3);
        images[3] = findViewById(R.id.image_4);
        images[4] = findViewById(R.id.image_5);
        images[5] = findViewById(R.id.image_6);
        images[6] = findViewById(R.id.image_7);
        images[7] = findViewById(R.id.image_8);

        prepareGrid();
    }

    public void openCamera(View view) {
        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);
    }

    public void prepareGrid() {
        File picture = new File(getFilesDir(), "my_picture.jpg");
        if (picture.isFile()) {
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(picture));
                Bitmap[] grid = gridImage(cropImage(bitmap));

                for (int i = 0; i < images.length; i++) {
                    images[i].(grid[i]);
                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }
    }

    public Bitmap[] gridImage(Bitmap source) {
        Bitmap[] grid = new Bitmap[9];

        int size = source.getWidth() / 3;

        int i = 0;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                int x = col * size;
                int y = row * size;
                grid[i] = Bitmap.createBitmap(source, x, y, size, size);
                i++;
            }
        }

        return grid;
    }

    public Bitmap cropImage(Bitmap source) {
        int x = 0;
        int y = 0;
        int size;

        Matrix matrix = new Matrix();
        matrix.postRotate(90);

        if (source.getHeight() > source.getWidth()) {
            size = source.getWidth();
            y = (source.getHeight() - size) / 2;
        } else {
            size = source.getHeight();
            x = (source.getWidth() - size) / 2;
        }

        return Bitmap.createBitmap(source, x, y, size, size, matrix, true);
    }
}
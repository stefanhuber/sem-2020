package fhku.intents;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.IntentCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    protected Button button1;
    protected Button button2;
    protected Button button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button1) {
            // Expliziter Intent zum Starten einer Activity die Teil der App ist
            // OtherActivity muss im Manifest aufgeführt sein, sonst würde der Intent nicht funktionieren
            Intent explicitIntent = new Intent(this, OtherActivity.class);
            startActivity(explicitIntent);
        } else if (v.getId() == R.id.button2) {
            // Impliziter Intent zum Senden einer SMS
            // Daten werden als URI angegeben, dies führt dazu dass der Intent Filter der SMS App aktiviert wird
            Intent implicitIntent1 = new Intent();
            implicitIntent1.setAction(Intent.ACTION_SENDTO);
            implicitIntent1.setData(Uri.parse("sms:+43123456789"));
            implicitIntent1.putExtra("sms_body", "Der textliche Inhalt der SMS");
            startActivity(implicitIntent1);
        } else if (v.getId() == R.id.button3) {
            // Impliziter Intent zum Öffnen der Kamera
            Intent implicitIntent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivity(implicitIntent2);
        }
    }
}
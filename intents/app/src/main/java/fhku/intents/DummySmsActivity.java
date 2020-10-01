package fhku.intents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DummySmsActivity extends AppCompatActivity {

    protected TextView smsBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy_sms);

        smsBody = findViewById(R.id.sms_body);
        Intent intent = getIntent();

        if (intent.hasExtra("sms_body")) {
            smsBody.setText(intent.getStringExtra("sms_body"));
        }
    }
}
package com.example.assignment_athirahizzati_oct2025;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {

    TextView tvAboutUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        tvAboutUrl = findViewById(R.id.tvAboutUrl);
        tvAboutUrl.setOnClickListener(v -> {
            String url = "https://github.com/athirah-sys/ElectricityEstimatorApp";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        });
    }
}

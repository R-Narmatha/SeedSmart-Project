package com.example.seedsmartapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.seedsmartapp.R;

public class MainActivity extends AppCompatActivity {

    CardView cardStart, cardHistory, cardSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cardStart = findViewById(R.id.cardStart);
        cardHistory = findViewById(R.id.cardHistory);
        cardSettings = findViewById(R.id.cardSettings);

        // Start Prediction
        cardStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CropInputActivity.class));
            }
        });

        // History
        cardHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HistoryActivity.class));
            }
        });

        // Settings
        cardSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            }
        });


    }
}
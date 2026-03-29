package com.example.seedsmartapp.activities;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;
import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.seedsmartapp.R;
import com.example.seedsmartapp.database.DatabaseHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SettingsActivity extends AppCompatActivity {

    Button btnClearHistory, btnAbout, btnLogout;
    Switch switchDarkMode;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // ✅ DARK MODE BEFORE UI
        SharedPreferences prefs = getSharedPreferences("settings", MODE_PRIVATE);
        boolean isDark = prefs.getBoolean("dark_mode", false);

        if (isDark) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        btnClearHistory = findViewById(R.id.btnClearHistory);
        btnAbout = findViewById(R.id.btnAbout);
        btnLogout = findViewById(R.id.btnLogout);
        switchDarkMode = findViewById(R.id.switchDarkMode);

        db = new DatabaseHelper(this);

        // =========================
        // 🌙 DARK MODE
        // =========================
        switchDarkMode.setChecked(isDark);

        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {

            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("dark_mode", isChecked);
            editor.apply();

            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }

            recreate();
        });

        // =========================
        // 🗑️ CLEAR HISTORY
        // =========================
        btnClearHistory.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Clear History")
                    .setMessage("Are you sure you want to delete all history?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        db.deleteAll();
                        Toast.makeText(this, "History Cleared", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        // =========================
        // ℹ️ ABOUT
        // =========================
        btnAbout.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("About SeedSmart")
                    .setMessage("SeedSmart 🌱\n\nAI-powered crop recommendation app.\n")
                    .setPositiveButton("OK", null)
                    .show();
        });
        btnLogout.setOnClickListener(v -> {

            new AlertDialog.Builder(this)
                    .setTitle("Logout")
                    .setMessage("Are you sure you want to logout?")
                    .setPositiveButton("Yes", (dialog, which) -> {

                        // 🔥 SIGN OUT
                        FirebaseAuth.getInstance().signOut();

                        Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();

                        // 🚀 GO TO LOGIN
                        Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);

                        // ❗ CLEAR BACK STACK
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                        startActivity(intent);
                        finish();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        // =========================
        // 🔥 BOTTOM NAVIGATION
        // =========================
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation);

        bottomNav.setSelectedItemId(R.id.nav_settings);

        bottomNav.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.nav_home) {
                startActivity(new Intent(this, MainActivity.class));
                finish();
                return true;
            }
            else if (item.getItemId() == R.id.nav_history) {
                startActivity(new Intent(this, HistoryActivity.class));
                finish();
                return true;
            }
            else if (item.getItemId() == R.id.nav_settings) {
                return true;
            }

            return false;
        });
    }
}
package com.example.seedsmartapp.activities;

import android.database.Cursor;
import android.os.Bundle;

import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seedsmartapp.R;
import com.example.seedsmartapp.adapters.HistoryAdapter;
import com.example.seedsmartapp.database.DatabaseHelper;
import com.example.seedsmartapp.models.HistoryModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<HistoryModel> list;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerView = findViewById(R.id.recyclerHistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = new DatabaseHelper(this);
        list = new ArrayList<>();

        String userId;

        if (com.google.firebase.auth.FirebaseAuth.getInstance().getCurrentUser() != null) {
            userId = com.google.firebase.auth.FirebaseAuth.getInstance().getCurrentUser().getUid();
        } else {
            userId = "guest";
        }

        Cursor cursor = db.getUserData(userId);

        while (cursor.moveToNext()) {
            list.add(new HistoryModel(
                    cursor.getInt(0),   // ID
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4)
            ));
        }

        final HistoryAdapter[] adapter = new HistoryAdapter[1];

        adapter[0] = new HistoryAdapter(list, position -> {

            new android.app.AlertDialog.Builder(this)
                    .setTitle("Delete")
                    .setMessage("Are you sure you want to delete this item?")
                    .setPositiveButton("Delete", (dialog, which) -> {

                        int id = list.get(position).getId();

                        db.deleteData(id);

                        list.remove(position);
                        adapter[0].notifyItemRemoved(position);

                        Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();

        });

        recyclerView.setAdapter(adapter[0]);
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation);

        bottomNav.setSelectedItemId(R.id.nav_history);

        bottomNav.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.nav_home) {
                startActivity(new Intent(this, MainActivity.class));
                finish();
                return true;
            }
            else if (item.getItemId() == R.id.nav_history) {
                return true;
            }
            else if (item.getItemId() == R.id.nav_settings) {
                startActivity(new Intent(this, SettingsActivity.class));
                finish();
                return true;
            }

            return false;
        });
    }
}
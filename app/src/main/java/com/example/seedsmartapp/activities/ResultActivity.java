package com.example.seedsmartapp.activities;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.seedsmartapp.R;
import com.example.seedsmartapp.ml.CropModelHelper;
import com.example.seedsmartapp.database.DatabaseHelper;

public class ResultActivity extends AppCompatActivity {

    TextView tvCrop, tvFertilizer, tvCost, tvTime;
    Button btnBack;
    ImageView imgCrop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        tvCrop = findViewById(R.id.tvCrop);
        tvFertilizer = findViewById(R.id.tvFertilizer);
        tvCost = findViewById(R.id.tvCost);
        tvTime = findViewById(R.id.tvTime);
        btnBack = findViewById(R.id.btnBack);
        imgCrop = findViewById(R.id.imgCrop);

        // =========================
        // 📥 GET INPUT DATA
        // =========================
        float n = getIntent().getFloatExtra("N", 0);
        float p = getIntent().getFloatExtra("P", 0);
        float k = getIntent().getFloatExtra("K", 0);
        float temp = getIntent().getFloatExtra("TEMP", 0);
        float humidity = getIntent().getFloatExtra("HUMIDITY", 0);
        float ph = getIntent().getFloatExtra("PH", 0);
        float rainfall = getIntent().getFloatExtra("RAINFALL", 0);

        // =========================
        // ❌ VALIDATION (IMPORTANT)
        // =========================

        if (n <=0 || n > 140) {
            Toast.makeText(this, "Nitrogen must be between 0–140", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        if (p <= 0 || p > 145) {
            Toast.makeText(this, "Phosphorus must be between 0–145", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        if (k <= 0 || k > 205) {
            Toast.makeText(this, "Potassium must be between 0–205", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        if (temp <= 0 || temp > 50) {
            Toast.makeText(this, "Temperature must be between 0–50°C", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        if (humidity <= 0 || humidity > 100) {
            Toast.makeText(this, "Humidity must be between 0–100%", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        if (ph <= 0 || ph > 14) {
            Toast.makeText(this, "pH must be between 0–14", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        if (rainfall <= 0 || rainfall > 300) {
            Toast.makeText(this, "Rainfall must be between 0–300 mm", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        // =========================
        // 🤖 ML MODEL
        // =========================
        CropModelHelper model = new CropModelHelper(this);
        float[] input = {n, p, k, temp, humidity, ph, rainfall};
        int index = model.predict(input);

        // =========================
        // 🌾 CROP LABELS
        // =========================
        String[] crops = {
                "apple", "banana", "blackgram", "chickpea", "coconut",
                "coffee", "cotton", "grapes", "jute", "kidneybeans",
                "lentil", "maize", "mango", "mothbeans", "mungbean",
                "muskmelon", "orange", "papaya", "pigeonpeas",
                "pomegranate", "rice", "watermelon"
        };

        String crop = crops[index];

        // =========================
        // 🖼️ IMAGE SET
        // =========================
        int imageResId = getResources().getIdentifier(
                crop.toLowerCase(),
                "drawable",
                getPackageName()
        );

        if (imageResId != 0) {
            imgCrop.setImageResource(imageResId);
        } else {
            imgCrop.setImageResource(R.drawable.default_crop);
        }

        // =========================
        // 🌱 FERTILIZER LOGIC
        // =========================
        String fertilizer;

        if (n < 50) {
            fertilizer = "Urea (Nitrogen rich)";
        } else if (p < 50) {
            fertilizer = "DAP (Phosphorus rich)";
        } else if (k < 50) {
            fertilizer = "MOP (Potassium rich)";
        } else {
            fertilizer = "Balanced NPK fertilizer";
        }

        // =========================
        // ⏳ TIME
        // =========================
        String time;

        switch (crop.toLowerCase()) {
            case "rice":
                time = "3-4 months";
                break;
            case "wheat":
                time = "4 months";
                break;
            case "maize":
                time = "3 months";
                break;
            case "cotton":
                time = "5-6 months";
                break;
            default:
                time = "3-5 months";
        }

        // =========================
        // 💰 COST
        // =========================
        String cost = "₹" + (3000 + (int)(Math.random() * 3000)) + " / acre";

        // =========================
        // 💾 SAVE TO DATABASE
        // =========================
        DatabaseHelper db = new DatabaseHelper(this);
        String userId;

        if (com.google.firebase.auth.FirebaseAuth.getInstance().getCurrentUser() != null) {
            userId = com.google.firebase.auth.FirebaseAuth.getInstance().getCurrentUser().getUid();
        } else {
            userId = "guest";
        }

        db.insertData(userId, crop, fertilizer, cost, time);

        // =========================
        // 📺 SET UI
        // =========================
        tvCrop.setText("Crop: " + crop);
        tvFertilizer.setText("Fertilizer: " + fertilizer);
        tvCost.setText("Estimated Cost: " + cost);
        tvTime.setText("Growth Time: " + time);

        // =========================
        // 🔙 BACK BUTTON
        // =========================
        btnBack.setOnClickListener(v -> finish());
    }
}
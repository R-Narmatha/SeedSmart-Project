package com.example.seedsmartapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.example.seedsmartapp.R;

public class CropInputActivity extends AppCompatActivity {

    EditText etNitrogen, etPhosphorus, etPotassium, etTemperature, etHumidity, etPH, etRainfall;
    Button btnPredict, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_input);

        etNitrogen = findViewById(R.id.etNitrogen);
        etPhosphorus = findViewById(R.id.etPhosphorus);
        etPotassium = findViewById(R.id.etPotassium);
        etTemperature = findViewById(R.id.etTemperature);
        etHumidity = findViewById(R.id.etHumidity);
        etPH = findViewById(R.id.etPH);
        etRainfall = findViewById(R.id.etRainfall);

        btnPredict = findViewById(R.id.btnPredict);
        btnBack = findViewById(R.id.btnBack); // ✅ ADD THIS IN XML

        btnPredict.setOnClickListener(v -> validateAndProceed());

        // ✅ BACK BUTTON (GO TO HOME)
        btnBack.setOnClickListener(v -> finish());
    }

    private void validateAndProceed() {

        // ✅ CHECK ALL FIELDS
        if (TextUtils.isEmpty(etNitrogen.getText()) ||
                TextUtils.isEmpty(etPhosphorus.getText()) ||
                TextUtils.isEmpty(etPotassium.getText()) ||
                TextUtils.isEmpty(etTemperature.getText()) ||
                TextUtils.isEmpty(etHumidity.getText()) ||
                TextUtils.isEmpty(etPH.getText()) ||
                TextUtils.isEmpty(etRainfall.getText())) {

            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        float n = Float.parseFloat(etNitrogen.getText().toString());
        float p = Float.parseFloat(etPhosphorus.getText().toString());
        float k = Float.parseFloat(etPotassium.getText().toString());
        float temp = Float.parseFloat(etTemperature.getText().toString());
        float humidity = Float.parseFloat(etHumidity.getText().toString());
        float ph = Float.parseFloat(etPH.getText().toString());
        float rainfall = Float.parseFloat(etRainfall.getText().toString());

        // 👉 MOVE TO RESULT
        Intent intent = new Intent(this, ResultActivity.class);

        intent.putExtra("N", n);
        intent.putExtra("P", p);
        intent.putExtra("K", k);
        intent.putExtra("TEMP", temp);
        intent.putExtra("HUMIDITY", humidity);
        intent.putExtra("PH", ph);
        intent.putExtra("RAINFALL", rainfall);

        startActivity(intent);
    }

    // ✅ CLEAR INPUT WHEN RETURNING
    @Override
    protected void onResume() {
        super.onResume();

        etNitrogen.setText("");
        etPhosphorus.setText("");
        etPotassium.setText("");
        etTemperature.setText("");
        etHumidity.setText("");
        etPH.setText("");
        etRainfall.setText("");
    }
}
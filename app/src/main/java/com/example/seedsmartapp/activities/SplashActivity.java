package com.example.seedsmartapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.*;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.seedsmartapp.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TextView tvAppName = findViewById(R.id.tvAppName);
        TextView tvTagline = findViewById(R.id.tvTagline);
        ImageView imgLogo = findViewById(R.id.imgLogo);

// Zoom animation for logo
        imgLogo.animate().scaleX(1.2f).scaleY(1.2f).setDuration(1500).start();


// Fade-in animation for App Name
        AlphaAnimation fadeIn = new AlphaAnimation(0f, 1f);
        fadeIn.setDuration(1500);
        fadeIn.setFillAfter(true);
        tvAppName.startAnimation(fadeIn);

// Fade-in animation for Tagline after App Name
        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                AlphaAnimation fadeInTag = new AlphaAnimation(0f,1f);
                fadeInTag.setDuration(1500);
                fadeInTag.setFillAfter(true);
                tvTagline.startAnimation(fadeInTag);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

// Move to Login after 3 seconds
        tvTagline.postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();
        }, 3000);

    }
}

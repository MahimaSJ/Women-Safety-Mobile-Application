package com.example.womensafetyapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.womensafetyapp.R;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the username passed from LoginActivity
        String username = getIntent().getStringExtra("USERNAME");

        // Display the welcome message
        TextView welcomeTextView = findViewById(R.id.welcomeTextView);
        if (username != null) {
            welcomeTextView.setText("Welcome, " + username + "!");
        }

        // Navigate to EmergencyActivity
        findViewById(R.id.btnEmergency).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, EmergencyActivity.class));
        });

        // Navigate to CalculatorActivity
        findViewById(R.id.btnCalculator).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, CalculatorActivity.class));
        });

        // Navigate to LoginActivity
        findViewById(R.id.btnLogin).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        });

        // Navigate to AnimationActivity
        findViewById(R.id.btnAnimations).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AnimationActivity.class));
        });
    }
}
package com.example.womensafetyapp;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AnimationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        // Initialize views
        TextView titleText = findViewById(R.id.titleText);
        Button fadeButton = findViewById(R.id.fadeButton);
        ImageView rotateImage = findViewById(R.id.rotateImage);
        Button slideButton = findViewById(R.id.slideButton);
        ImageView zoomImage = findViewById(R.id.zoomImage);

        // Load animations
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Animation rotate = AnimationUtils.loadAnimation(this, R.anim.rotate);
        Animation slideIn = AnimationUtils.loadAnimation(this, R.anim.slide_in);
        Animation zoomIn = AnimationUtils.loadAnimation(this, R.anim.zoom_in);

        // Set fade animation to the title text
        titleText.startAnimation(fadeIn);

        // Apply animations on button clicks
        fadeButton.setOnClickListener(v -> titleText.startAnimation(fadeIn));

        rotateImage.setOnClickListener(v -> rotateImage.startAnimation(rotate));

        slideButton.setOnClickListener(v -> slideButton.startAnimation(slideIn));

        zoomImage.setOnClickListener(v -> zoomImage.startAnimation(zoomIn));
    }
}

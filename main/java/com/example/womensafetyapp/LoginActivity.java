package com.example.womensafetyapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DBHelper(this);

        findViewById(R.id.btnLogin).setOnClickListener(v -> {
            String username = ((EditText) findViewById(R.id.editUsername)).getText().toString();
            String password = ((EditText) findViewById(R.id.editPassword)).getText().toString();

            if (dbHelper.validateUser(username, password)) {
                Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show();

                // Pass the username to MainActivity
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("USERNAME", username);  // Add username to the Intent
                startActivity(intent);
                finish();  // Close LoginActivity
            } else {
                Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.btnRegister).setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }
}

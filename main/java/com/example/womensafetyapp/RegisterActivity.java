package com.example.womensafetyapp;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.womensafetyapp.R;

public class RegisterActivity extends AppCompatActivity {
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);  // Ensure the layout is defined in 'res/layout/activity_register.xml'

        dbHelper = new DBHelper(this);

        findViewById(R.id.btnRegister).setOnClickListener(v -> {
            // Get the input values from the EditText fields
            String username = ((EditText) findViewById(R.id.editUsername)).getText().toString();
            String password = ((EditText) findViewById(R.id.editPassword)).getText().toString();

            // Register the new user
            if (dbHelper.addUser(username, password)) {
                Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                finish();  // Close the activity and return to the previous one (usually LoginActivity)
            } else {
                Toast.makeText(this, "Error occurred!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

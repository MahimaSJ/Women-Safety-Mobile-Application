package com.example.womensafetyapp;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.womensafetyapp.R;

public class EmergencyActivity extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST_CODE = 100;
    private LinearLayout contactListLayout;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        // Initialize database
        db = openOrCreateDatabase("EmergencyContacts", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS Contacts (id INTEGER PRIMARY KEY, name TEXT, phone TEXT)");

        // Check permissions at runtime for Android M (6.0) and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.SEND_SMS
                }, PERMISSIONS_REQUEST_CODE);
            }
        }

        // Load animations
        Animation buttonClick = AnimationUtils.loadAnimation(this, R.anim.button_click);
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        // Initialize buttons and layout
        Button btnAddContact = findViewById(R.id.btnAddContact);
        Button btnSendAlert = findViewById(R.id.btnSendAlert);
        contactListLayout = findViewById(R.id.contactListLayout);

        // Apply fade-in animation to buttons on activity start
        btnAddContact.startAnimation(fadeIn);
        btnSendAlert.startAnimation(fadeIn);

        // Load stored contacts
        loadContacts();

        // Add Contact Button
        btnAddContact.setOnClickListener(v -> {
            v.startAnimation(buttonClick);  // Apply click animation
            addEmergencyContact();         // Add contact functionality
        });

        // Send Alert Button
        btnSendAlert.setOnClickListener(v -> {
            v.startAnimation(buttonClick);  // Apply click animation
            sendEmergencySMS();             // Send SMS functionality
        });
    }

    private void loadContacts() {
        Cursor cursor = db.rawQuery("SELECT * FROM Contacts", null);
        contactListLayout.removeAllViews();

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(1);
                String phone = cursor.getString(2);
                addContactToUI(name, phone);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    private void addEmergencyContact() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Emergency Contact");

        // Create input fields for name and phone number
        EditText inputName = new EditText(this);
        inputName.setHint("Enter Name");

        EditText inputPhone = new EditText(this);
        inputPhone.setHint("Enter Phone Number");

        // Add input fields to the dialog
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(inputName);
        layout.addView(inputPhone);
        builder.setView(layout);

        builder.setPositiveButton("Save", (dialog, which) -> {
            String name = inputName.getText().toString().trim();
            String phone = inputPhone.getText().toString().trim();

            if (name.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "Please fill out all fields.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Save to database
            ContentValues values = new ContentValues();
            values.put("name", name);
            values.put("phone", phone);
            db.insert("Contacts", null, values);

            // Update UI
            addContactToUI(name, phone);
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.create().show();
    }

    private void addContactToUI(String name, String phone) {
        LinearLayout contactItem = new LinearLayout(this);
        contactItem.setOrientation(LinearLayout.HORIZONTAL);

        TextView contactText = new TextView(this);
        contactText.setText(String.format("%s: %s", name, phone));
        contactText.setLayoutParams(new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));

        Button deleteButton = new Button(this);
        deleteButton.setText("Delete");
        deleteButton.setOnClickListener(v -> {
            db.delete("Contacts", "phone=?", new String[]{phone});
            contactListLayout.removeView(contactItem);
        });

        contactItem.addView(contactText);
        contactItem.addView(deleteButton);
        contactListLayout.addView(contactItem);
    }

    private void sendEmergencySMS() {
        Cursor cursor = db.rawQuery("SELECT phone FROM Contacts", null);

        if (cursor.moveToFirst()) {
            do {
                String phone = cursor.getString(0);
                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phone, null, "Emergency Alert! Please check my location.", null, null);
                } catch (Exception e) {
                    Toast.makeText(this, "Failed to send alert to " + phone, Toast.LENGTH_SHORT).show();
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        Toast.makeText(this, "Alert Sent to all contacts!", Toast.LENGTH_SHORT).show();
    }
}

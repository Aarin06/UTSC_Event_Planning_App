package com.example.cscb07_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class AdminVenue extends AppCompatActivity {

    String venueName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_venue);

        Intent intent = getIntent();

        venueName = intent.getStringExtra("venue");

        TextView tv = findViewById(R.id.textView3);

        tv.setText(venueName);

    }
}
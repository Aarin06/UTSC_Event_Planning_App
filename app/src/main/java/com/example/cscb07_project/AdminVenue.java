package com.example.cscb07_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

public class AdminVenue extends AppCompatActivity {

    String venueName;
    private DatabaseReference fire;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_venue);

        Intent intent = getIntent();

        venueName = intent.getStringExtra("venue");

        TextView tv = findViewById(R.id.textView3);

        tv.setText(venueName);

        //Set up approve events button
        Button approveButton = (Button) findViewById(R.id.button3);
        approveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent i = new Intent(AdminVenue.this, ApproveEvents.class);
                i.putExtra("venue", venueName);

                startActivity(i);

            }
        });

    }
}
package com.example.cscb07_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

public class AdminVenue extends AppCompatActivity {

    String venueID;
    String venueName;
    private DatabaseReference fire;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_venue);

        Intent intent = getIntent();

        venueID = intent.getStringExtra("venue");
        venueName = intent.getStringExtra("venueName");

        TextView tv = findViewById(R.id.textView3);

        tv.setText(venueName);

        //Set up approve events button
        Button approveButton = (Button) findViewById(R.id.button3);
        approveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent i = new Intent(AdminVenue.this, ApproveEvents.class);
                i.putExtra("venue", venueID);

                startActivity(i);

            }
        });

    }
}
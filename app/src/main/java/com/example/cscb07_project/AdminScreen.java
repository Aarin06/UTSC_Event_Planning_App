package com.example.cscb07_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class AdminScreen extends AppCompatActivity{

    private DatabaseReference fire;
    private String[] venueNames;
    private AutoCompleteTextView venueSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_screen);

        fire = FirebaseDatabase.getInstance().getReference("Venues");

        //Set up enter button
        Button enterButton = (Button) findViewById(R.id.button);
        enterButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                enter();
            }
        });

        //Set up add venue button
        Button addVenueButton = (Button) findViewById(R.id.button2);
        addVenueButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //Cat add code here to go to other activity or do whatever you need to do

            }
        });

        //Add list of venues as selectable options
        fire.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {

            //Set up text input
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                venueNames = new String[(int) task.getResult().getChildrenCount()];

                int i = 0;

                for (DataSnapshot ds : task.getResult().getChildren()) {
                    venueNames[i++] = ds.getKey();
                }

                venueSearch = findViewById(R.id.autoCompleteTextView);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(AdminScreen.this, android.R.layout.simple_list_item_1, venueNames);

                venueSearch.setThreshold(1);
                venueSearch.setAdapter(adapter);


            }
        });


    }

    private void enter() {

        String currText = venueSearch.getText().toString();

        // Check if text is valid
        for (String s: venueNames) {

            if (s.equals(currText)) {

                Intent i = new Intent(this, AdminVenue.class);
                i.putExtra("venue", s);

                startActivity(i);
                return;
            }

        }
        Toast.makeText(AdminScreen.this, "Invalid Venue", Toast.LENGTH_LONG).show();
    }
}


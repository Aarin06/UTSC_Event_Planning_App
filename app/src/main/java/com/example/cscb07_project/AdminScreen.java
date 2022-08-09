package com.example.cscb07_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdminScreen extends AppCompatActivity implements com.example.cscb07_project.venueAdapter.OnNoteListener{

    private DatabaseReference fire;
    private ArrayList<Venue> venues;
    private ArrayList<String> venueIDs;
    private AutoCompleteTextView venueSearch;
    private String uID;

    private RecyclerView rv;
    private LinearLayoutManager llm;
    private venueAdapter adapater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_screen);

        fire = FirebaseDatabase.getInstance().getReference("Venues");
        uID = getSharedPreferences("myprefs", Context.MODE_PRIVATE).getString("uID", "N/A");

        //Set up add venue button
        Button addVenueButton = (Button) findViewById(R.id.button2);
        addVenueButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent (AdminScreen.this, CreateNewVenue.class); //context is AdminScreen.this
                startActivity(intent);
            }
        });

        //Add list of venues as selectable options
        fire.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {

            //Set up text input
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                venues = new ArrayList<Venue>();
                venueIDs = new ArrayList<String>();

                for (DataSnapshot ds : task.getResult().getChildren()) {

                    if (ds.child("creatorID").getValue(String.class).equals(uID)){
                        Venue v = getVenue(ds);
                        venues.add(v);
                        venueIDs.add(ds.getKey());
                    }
                }

                recyclerInit();

            }
        });


    }

    private Venue getVenue(DataSnapshot ds) {
        String creatorID = ds.child("creatorID").getValue().toString();
        String location = ds.child("location").getValue().toString();
        String name = ds.child("name").getValue().toString();
        String openTime = ds.child("openTime").getValue().toString();
        String venueID = ds.child("venueID").getValue().toString();

        ArrayList<Event> eventsList = new ArrayList<>();
        DataSnapshot events = ds.child("eventsList");

        if (events.hasChildren()) {
            for (DataSnapshot dataSnapshot1 : events.getChildren()) {
                String eventName = dataSnapshot1.child("eventName").getValue().toString();
                String creator1ID = dataSnapshot1.child("creatorID").getValue().toString();
                String eventID = dataSnapshot1.child("eventID").getValue().toString();
                int maxPlayers = Integer.parseInt(dataSnapshot1.child("maxPlayers").getValue().toString());
                int numPlayers = Integer.parseInt(dataSnapshot1.child("numPlayers").getValue().toString());
                String startTime = dataSnapshot1.child("startTime").getValue().toString();
                String endTime = dataSnapshot1.child("endTime").getValue().toString();
                String date = dataSnapshot1.child("date").getValue().toString();
                boolean eventApproved = Boolean.parseBoolean(dataSnapshot1.child("eventApproved").getValue().toString());

                ArrayList<String> enrolledPlayers = new ArrayList<>();
                DataSnapshot players = dataSnapshot1.child("enrolledPlayers");
                if (players.hasChildren()) {
                    for (DataSnapshot dataSnapshot2 : players.getChildren()) {
                        String player = dataSnapshot2.getValue().toString();
                        enrolledPlayers.add(player);
                    }
                }
                Event e = new Event(eventID, creator1ID, maxPlayers, numPlayers, eventName ,enrolledPlayers, startTime, endTime, eventApproved, date);
            }
        }
        ArrayList<String> sportsOffered = new ArrayList<>();
        DataSnapshot offered = ds.child("sportsOffered");

        if (offered.hasChildren()) {
            for (DataSnapshot dataSnapshot1 : offered.getChildren()) {
                String sport = dataSnapshot1.getValue().toString();
                sportsOffered.add(sport);
            }
        }

        return new Venue(creatorID,location,name,openTime,sportsOffered,venueID,eventsList);
    }

    private void recyclerInit(){

        RecyclerView rv = findViewById(R.id.venueList);
        llm = new LinearLayoutManager(this);
        llm.setOrientation(RecyclerView.VERTICAL);
        rv.setLayoutManager(llm);
        adapater = new venueAdapter(this, venues, this);
        rv.setAdapter(adapater);

    }

    @Override
    public void onNoteClick(int position) {
        Venue v = venues.get(position);

        Intent intent = new Intent(this,AdminVenue.class);
        intent.putExtra("venueName", v.name);
        intent.putExtra("venue", venueIDs.get(position));

        startActivity(intent);
    }
}


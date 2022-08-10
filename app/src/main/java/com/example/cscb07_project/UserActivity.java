package com.example.cscb07_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity implements com.example.cscb07_project.venueAdapter.OnNoteListener {
    public static final String VENUE = "com.example.cscb07_project.VENUE_ID";
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    venueAdapter venueAdapter;
    ArrayList<Venue> vlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        recyclerView = findViewById(R.id.venueList);
        databaseReference = FirebaseDatabase.getInstance().getReference("Venues");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        vlist = new ArrayList<>();
        venueAdapter = new venueAdapter(this,vlist,this);
        recyclerView.setAdapter(venueAdapter);
        update();

    }

    @Override
    public void onNoteClick(int position) {
        System.out.println("clicked");
        Venue v = vlist.get(position);

        Intent intent = new Intent(this,EventsActivity.class);
        intent.putExtra(VENUE, v.name);

        startActivity(intent);
    }

    public void goHome(View view) {
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
    }

    public void myEvents(View view) {
        Intent intent = new Intent(this, ViewUserEventsActivity.class);
        startActivity(intent);
    }

    public void update(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String creatorID = dataSnapshot.child("creatorID").getValue().toString();
                    String location = dataSnapshot.child("location").getValue().toString();
                    String name = dataSnapshot.child("name").getValue().toString();
                    String openTime = dataSnapshot.child("openTime").getValue().toString();
                    String venueID = dataSnapshot.child("venueID").getValue().toString();

                    ArrayList<Event> eventsList = new ArrayList<>();
                    DataSnapshot events = dataSnapshot.child("eventsList");
                    //check for children
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
                            Event e = new Event(eventID, creator1ID, maxPlayers, numPlayers, eventName ,enrolledPlayers, startTime, endTime, eventApproved,date);
                        }
                    }
                    ArrayList<String> sportsOffered = new ArrayList<>();
                    DataSnapshot offered = dataSnapshot.child("sportsOffered");
                    if (offered.hasChildren()) {
                        for (DataSnapshot dataSnapshot1 : offered.getChildren()) {
                            String sport = dataSnapshot1.getValue().toString();
                            sportsOffered.add(sport);
                        }
                    }
                    Venue v = new Venue(creatorID,location,name,openTime,sportsOffered,venueID,eventsList);
                    for (Venue temp : vlist){
                        if (temp.venueID == v.venueID){
                            vlist.remove(temp);
                        }
                    }
                    vlist.add(v);

                }
                venueAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



}
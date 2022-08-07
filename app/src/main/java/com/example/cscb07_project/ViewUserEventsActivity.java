package com.example.cscb07_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ViewUserEventsActivity extends AppCompatActivity {
    // Useful booleans.
    private boolean setup = false;
    private boolean visibleRefresh = false;
    // FireBase Variables.
    private DatabaseReference fire;
    private DatabaseReference userRef;
    // Event listener.
    private ValueEventListener listen;
    // User object.
    private String userID;
    private User user;
    // Android Objects.
    private RecyclerView created;
    private RecyclerView joined;
    private Button refresh;
    // Events Recycler View Adapter.
    EventsRecyclerAdapter adapterCreated;
    EventsRecyclerAdapter adapterJoined;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Changing the view so the user can see.
        setContentView(R.layout.activity_view_user_events);
        // Getting the user ID from shared preferences.
        SharedPreferences p = getSharedPreferences("myprefs", Context.MODE_PRIVATE);
        this.userID = p.getString("uID", "N/A");
        // Somehow the user got to this screen without logging in.
        if (userID.equals("N/A")) startActivity(new Intent(this, MainActivity.class));
        // Set-up.
        fire = FirebaseDatabase.getInstance().getReference();
        userRef = fire.child("Users").child(userID);
        // Setting up android object references.
        this.refresh = findViewById(R.id.button_refresh_events);
        this.refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(getIntent());
            }
        });
        // RecyclerView references.
        this.created = findViewById(R.id.recycle_created_events);
        this.joined = findViewById(R.id.recycle_joined_events);
        // Creating the user object.
        userRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                // If failed to read.
                if (!task.isSuccessful()) {
                    System.out.println("FireBase Data Collection Error: " + task.getException().toString());
                    Toast.makeText(ViewUserEventsActivity.this, "There was an error getting the data from FireBase!", Toast.LENGTH_LONG).show();
                    return;
                }
                // Otherwise.
                ViewUserEventsActivity.this.user = new User(task);
                // After user object is populated, start the listener.
                ViewUserEventsActivity.this.startListener();
            }
        });
    }
    private void startListener() {
        // Setting up an event listener to listen for changes in venues (really only for events).
        listen = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Update the FireBase references.
                fire = snapshot.getRef();
                userRef = fire.child("Users").child(ViewUserEventsActivity.this.userID);
                // If first call, update data directly.
                if (!ViewUserEventsActivity.this.setup) {
                    ViewUserEventsActivity.this.updateData();
                    ViewUserEventsActivity.this.setup = true;
                }
                // Otherwise, display a refresh button.
                else displayRefresh();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("FireBase Data Collection Error: " + error.toString());
                Toast.makeText(ViewUserEventsActivity.this, "There was an error getting the data from FireBase!", Toast.LENGTH_LONG).show();
            }
        };
        // Adding a listener to the database.
        fire.addValueEventListener(listen);
    }
    private void displayRefresh() {
        if (this.visibleRefresh) return;
        // If the refresh button is not on screen, display it.
        this.refresh.setAlpha(1);
        this.refresh.setClickable(true);
        this.visibleRefresh = true;
    }
    private void hideRefresh() {
        if (!this.visibleRefresh) return;
        // If the refresh button is on screen, hide it.
        this.refresh.setAlpha(0);
        this.refresh.setClickable(false);
        this.visibleRefresh = false;
    }
    private void updateData() {
        // Updating the User object.
        userRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                // If failed to read.
                if (!task.isSuccessful()) {
                    System.out.println("FireBase Data Collection Error: " + task.getException().toString());
                    Toast.makeText(ViewUserEventsActivity.this, "There was an error getting the data from FireBase!", Toast.LENGTH_LONG).show();
                    return;
                }
                // Otherwise.
                ViewUserEventsActivity.this.user = new User(task);
                // Creating the views.
                // Created Events.
                created.setHasFixedSize(true);
                created.setLayoutManager(new LinearLayoutManager(ViewUserEventsActivity.this, LinearLayoutManager.HORIZONTAL, false));
                // Joined Events.
                joined.setHasFixedSize(true);
                joined.setLayoutManager(new LinearLayoutManager(ViewUserEventsActivity.this, LinearLayoutManager.HORIZONTAL, false));
                fire.child("Venues").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        ArrayList<Event> createdEvents = new ArrayList<Event>();
                        ArrayList<Event> joinedEvents = new ArrayList<Event>();
                        // Looping through joined events.
                        for (Object eID : ViewUserEventsActivity.this.user.getEventsJoined().values()) {
                            Event e = new Event(task, (String) eID);
                            System.out.println(e.toString());
                            if (!(e.getEventID().equals("N/A"))) joinedEvents.add(e);
                        }
                        // Looping through created events.
                        for (Object eID : ViewUserEventsActivity.this.user.getEventsCreated().values()) {
                            Event e = new Event(task, (String) eID);
                            if (!(e.getEventID().equals("N/A"))) createdEvents.add(e);
                        }
                        // Adapters.
                        adapterJoined = new EventsRecyclerAdapter(ViewUserEventsActivity.this, joinedEvents, "Pan Am Sports Centre");
                        adapterCreated = new EventsRecyclerAdapter(ViewUserEventsActivity.this, createdEvents, "Pan Am Sports Centre");
                        // Setting the adapters.
                        created.setAdapter(adapterCreated);
                        joined.setAdapter(adapterJoined);
                    }
                });
            }
        });
    }
}
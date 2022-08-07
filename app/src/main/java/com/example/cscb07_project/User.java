package com.example.cscb07_project;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.*; //java.util.List

public class User extends Account {
    // User fields.
    private String name;
    private String email;
    private String userID;
    private int status;
    // Lists of events.
    private HashMap<String, Object> eventsCreated;
    private HashMap<String, Object> eventsJoined;

    private User() {}

    public User(@NonNull Task<DataSnapshot> task) {
        DataSnapshot res = task.getResult();
        // If the user does not exist.
        if(!res.exists()) {
            User.this.userID = "N/A";
            // Return early.
            return;
        }
        // Set all the user values.
        User.this.email = res.child("email").getValue().toString();
        // Filling up the lists.
        // Created events.
        User.this.eventsCreated = new HashMap<String, Object>();
        DataSnapshot events = res.child("eventsCreated");
        if (events.exists()) {
            // Loop through all events and add them to the list.
            for (DataSnapshot e : events.getChildren()) User.this.eventsCreated.put(e.getKey(), e.getValue());
        }
        // Joined events.
        User.this.eventsJoined = new HashMap<String, Object>();
        events = res.child("eventsCreated");
        if (events.exists()) {
            // Loop through all events and add them to the list.
            for (DataSnapshot e : events.getChildren()) User.this.eventsJoined.put(e.getKey(), e.getValue());
        }
        // The rest of the fields.
        User.this.name = res.child("name").getValue().toString();
        User.this.status = Integer.parseInt(res.child("status").getValue().toString());
        User.this.userID = res.child("userID").getValue().toString();
    }

    public User(String name, String email, String userID) {
        this.name = name;
        this.email = email;
        this.userID = userID;
        this.status = 0;
    }

    // Getters for FireBase class marshaling.
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getUserID() {
        return userID;
    }
    public HashMap<String, Object> getEventsCreated() {
        return eventsCreated;
    }
    public HashMap<String, Object> getEventsJoined() {
        return eventsJoined;
    }

    public void requestEvent (){
    }
    public boolean joinEvent(){
        return false;
    }

    // Added 08/01/2022 - Jason.
    public boolean leaveEvent() {
        return true;
    }

    @Override
    public String toString() {
        String result =  "==========================\n" +
                "User ID: " + this.getUserID() + "\n" +
                "Name: " + this.getName() + "\n" +
                "Email: " + this.getEmail() + "\n" +
                "Admin: " + this.status + "\n" +
                "Events Created: ";
        for (Object e : this.getEventsCreated().values()) result += (String) e + " ";
        result += "\nEvents Joined: ";
        for (Object e : this.getEventsJoined().values()) result += (String) e + " ";
        result += "\n==========================\n";
        return result;
    }
}

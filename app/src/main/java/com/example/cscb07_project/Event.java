package com.example.cscb07_project;


import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Event {
    String eventID;
    String creatorID;
    int maxPlayers; //can hold up to  //
    int numPlayers; //currently at  //
    String eventName;  //
    ArrayList<String> enrolledPlayers;
    String startTime; //java contains Date objects  //
    String endTime; //
    String date;    //
    boolean eventApproved;

    public Event(){}

    public Event(@NonNull Task<DataSnapshot> task, String eID) {
        // Loop through all the venues.
        for (DataSnapshot data : task.getResult().getChildren()) {
            DataSnapshot event = data.child("eventList").child(eID);
            // If the current venue does not contain the desired event.
            if (!event.exists()) continue;
            // Otherwise, if the event is found.
            // Setting all the fields.
            this.creatorID = event.child("creatorID").getValue().toString();
            this.date = event.child("date").getValue().toString();
            this.endTime = event.child("endTime").getValue().toString();
            this.eventApproved = event.child("eventApproved").getValue().toString().equals("true");
            this.eventID = event.child("eventID").getValue().toString();
            this.eventName = event.child("eventName").getValue().toString();
            this.maxPlayers = Integer.parseInt(event.child("maxPlayers").getValue().toString());
            this.numPlayers = Integer.parseInt(event.child("numPlayers").getValue().toString());
            this.startTime = event.child("startTime").getValue().toString();
            // Making the list for enrolled players.
            this.enrolledPlayers = new ArrayList<String>();
            for (long i = 0; i < event.child("enrolledPlayers").getChildrenCount(); i++) {
                this.enrolledPlayers.add(event.child("enrolledPlayers").child(String.valueOf(i)).getValue(String.class));
            }
            // Once all values are set, return early.
            return;
        }
        // If the event was not found.
        this.eventID = "N/A";
    }

    public Event(String eventID, String creatorID, int maxPlayers, int numPlayers, String eventName, ArrayList<String> enrolledPlayers, String startTime, String endTime, boolean eventApproved, String date) {

        this.eventID = eventID;
        this.creatorID = creatorID;
        this.maxPlayers = maxPlayers;
        this.numPlayers = numPlayers;
        this.eventName = eventName;
        this.enrolledPlayers = enrolledPlayers;
        this.startTime = startTime;
        this.endTime = endTime;
        this.eventApproved = eventApproved;
        this.date = date;

    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public void setCreatorID(String creatorID) {
        this.creatorID = creatorID;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public void setNumPlayers(int numPlayers) {

        this.numPlayers = numPlayers;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setEnrolledPlayers(ArrayList<String> enrolledPlayers) {
        this.enrolledPlayers = enrolledPlayers;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setEventApproved(boolean eventApproved) {
        this.eventApproved = eventApproved;
    }


    public String getEventID() {
        return eventID;
    }

    public String getDate() {
        return date;
    }

    public String getCreatorID() {
        return creatorID;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public int getNumPlayers() {

        return numPlayers;
    }

    public String getEventName() {
        return eventName;
    }


    public ArrayList<String> getEnrolledPlayers() {
        return enrolledPlayers;
    }


    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public boolean isEventApproved() {
        return eventApproved;
    }


    @Override
    public String toString(){
        return this.getEventName() + "" +
                "\nCreated by " + this.getCreatorID() +
                "\nStart time: " + this.getStartTime() + " End time:" + this.getEndTime() +
                "\nCurrent number of players: " + Long.toString(this.getNumPlayers()) +
                "\nMaximum number of players: " + Long.toString(this.getMaxPlayers()) +
                "\nEvent ID: " + this.getEventID();
    }

}


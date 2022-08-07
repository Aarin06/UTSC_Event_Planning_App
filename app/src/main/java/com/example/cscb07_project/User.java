package com.example.cscb07_project;

import java.util.*; //java.util.List

public class User extends Account {
    private ArrayList<Event> eventsCreated;
    private ArrayList<Event> eventsJoined;

    private User() {}

    public User(String name, String email, String userID) {
        this.name = name;
        this.email = email;
        this.userID = userID;
        status = 0;
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
    public ArrayList<Event> getEventsCreated() {
        return eventsCreated;
    }
    public ArrayList<Event> getEventsJoined() {
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
}

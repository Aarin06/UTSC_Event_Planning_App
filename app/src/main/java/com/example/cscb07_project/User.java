package com.example.cscb07_project;

import java.util.*; //java.util.List

public class User extends Account {
    List<Event> eventsCreated;
    List<Event> eventsJoined;

    public User(String name, String email, String userID) {
        this.name = name;
        this.email = email;
        this.userID = userID;
        status = 0;
    }

    public void requestEvent (){
    }
    public boolean joinEvent(){
        return false;
    }
}

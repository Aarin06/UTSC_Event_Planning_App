package com.example.cscb07_project;

import java.util.ArrayList;

public class Event {
    String eventID;
    String creatorID;
    int maxPlayers; //can hold up to
    int numPlayers; //currently at
    ArrayList<String> enrolledPlayers;
    String startTime; //java contains Date objects
    String endTime;
    boolean eventApproved;

    public Event(String eventID, String creatorID, int maxPlayers, int numPlayers, ArrayList<String> enrolledPlayers, String startTime, String endTime, boolean eventApproved) {
        this.eventID = eventID;
        this.creatorID = creatorID;
        this.maxPlayers = maxPlayers;
        this.numPlayers = numPlayers;
        this.enrolledPlayers = enrolledPlayers;
        this.startTime = startTime;
        this.endTime = endTime;
        this.eventApproved = eventApproved;
    }
}

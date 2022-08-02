package com.example.cscb07_project;

import com.google.firebase.database.GenericTypeIndicator;

import java.util.ArrayList;
import java.util.HashMap;

public class Event {
    String eventID;
    String creatorID;
    long maxPlayers; //can hold up to
    long numPlayers; //currently at
    String eventName;
    String startTime; //java contains Date objects
    String endTime;
    boolean eventApproved;

    ArrayList<String> enrolledPlayers ;

    public Event(){}

    public Event(String eventID, String creatorID, long maxPlayers, long numPlayers, String eventName, String startTime, String endTime, boolean eventApproved, ArrayList<String> enrolledPlayers) {
        this.eventID = eventID;
        this.creatorID = creatorID;
        this.maxPlayers = maxPlayers;
        this.numPlayers = numPlayers;
        this.eventName = eventName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.eventApproved = eventApproved;
        this.enrolledPlayers = enrolledPlayers;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public void setCreatorID(String creatorID) {
        this.creatorID = creatorID;
    }

    public void setMaxPlayers(long maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public void setNumPlayers(long numPlayers) {
        this.numPlayers = numPlayers;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setEventApproved(boolean eventApproved) {
        this.eventApproved = eventApproved;
    }

    public void setEnrolledPlayers(ArrayList<String> enrolledPlayers) {
        this.enrolledPlayers = enrolledPlayers;
    }

    public String getEventID() {
        return eventID;
    }

    public String getCreatorID() {
        return creatorID;
    }

    public long getMaxPlayers() {
        return maxPlayers;
    }

    public long getNumPlayers() {
        return numPlayers;
    }

    public String getEventName() {
        return eventName;
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

    public ArrayList<String> getEnrolledPlayers() {
        return enrolledPlayers;
    }

    @Override
    public String toString(){
        return this.getEventName() + "" +
                "\nCreated by " + this.getCreatorID() +
                "\nStart time: " + this.getStartTime() + " End time:" + this.getEndTime() +
                "\nCurrent number of players: " + Long.toString(this.getNumPlayers()) +
                "\nMaximum number of players: " + Long.toString(this.getMaxPlayers());
    }


}
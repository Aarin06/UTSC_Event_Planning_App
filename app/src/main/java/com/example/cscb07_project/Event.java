package com.example.cscb07_project;


import java.util.ArrayList;

public class Event {
    String eventID;
    String creatorID;
    int maxPlayers; //can hold up to
    int numPlayers; //currently at
    String eventName;
    ArrayList<String> enrolledPlayers;

    String startTime; //java contains Date objects
    String endTime;
    boolean eventApproved;


    public Event(){}

    public Event(String eventID, String creatorID, int maxPlayers, int numPlayers, String eventName, ArrayList<String> enrolledPlayers, String startTime, String endTime, boolean eventApproved) {

        this.eventID = eventID;
        this.creatorID = creatorID;
        this.maxPlayers = maxPlayers;
        this.numPlayers = numPlayers;
        this.eventName = eventName;

        this.enrolledPlayers = enrolledPlayers;
        this.startTime = startTime;
        this.endTime = endTime;
        this.eventApproved = eventApproved;

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

    public void setEventApproved(boolean eventApproved) {
        this.eventApproved = eventApproved;
    }


    public String getEventID() {
        return eventID;
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
                "\nMaximum number of players: " + Long.toString(this.getMaxPlayers());
    }

}


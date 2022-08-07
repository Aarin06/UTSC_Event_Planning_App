package com.example.cscb07_project;

import java.io.Serializable;
import java.util.ArrayList;

public class Venue implements Serializable {
    String creatorID;
    String location;
    String name;
    String openTime;
    ArrayList<String> sportsOffered;
    String venueID; //string/int according to our structure document
    ArrayList<Event> eventsList;

    public Venue(){
    }

    public Venue(String creatorID, String location, String name, String openTime, ArrayList<String> sportsOffered, String venueID,ArrayList<Event> eventsList) {
        this.creatorID = creatorID;
        this.location = location;
        this.name = name;
        this.openTime = openTime;
        this.sportsOffered = sportsOffered;
        this.venueID = venueID;
        this.eventsList = eventsList;
    }
    public String getVenueID() {
        return venueID;
    }

    public String getCreatorID() {
        return creatorID;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getOpenTime() {
        return openTime;
    }
    public ArrayList<String> getSportsOffered() {
        return sportsOffered;
    }

    public String StringSportsOffered() {
        String all="";
        for (String s : sportsOffered){
            if (all.length()>42){
                all+="\n";
            }
            String temp = s+"    ";
            all += temp;
        }
        if(all.substring(all.length() - 1).equals("\n")){
            all+="\n";
        }
        else{
            all+="\n\n";
        }

        return all;
    }

    public ArrayList<Event> getEventsList() {
        return eventsList;
    }
}

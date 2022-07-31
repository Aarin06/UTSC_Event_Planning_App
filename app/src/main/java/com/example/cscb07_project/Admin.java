package com.example.cscb07_project;

import java.util.List;

public class Admin extends Account {
    List<Event> venuesOwned;

    public Admin(String name, String email, String userID) {
        this.name = name;
        this.email = email;
        this.userID = userID;
        status = 1;
    }

    protected void createVenue(){

    }
    protected void approveEvent(){

    }
    protected void rejectEvent(){

    }
}

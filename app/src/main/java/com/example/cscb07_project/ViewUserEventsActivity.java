package com.example.cscb07_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

public class ViewUserEventsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Getting the user ID from shared preferences.
        //SharedPreferences p = getSharedPreferences("myprefs", Context.MODE_PRIVATE);
        //String userID = p.getString("uID", "");
        // Reading the user data from FireBase.
        setContentView(R.layout.activity_view_user_events);
    }
}
package com.example.cscb07_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EventsActivity extends AppCompatActivity implements View.OnClickListener{
    ArrayList<Event> events_list = new ArrayList<Event>();
    RecyclerView recyclerView;
    DatabaseReference database;
    EventListAdapter myAdapter;
    ArrayList<Event> list;

    private TextView back;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        recyclerView = findViewById(R.id.event_list);
        database = FirebaseDatabase.getInstance().getReference("Venues");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        SharedPreferences p = getSharedPreferences("myprefs", Context.MODE_PRIVATE);
        Intent intent = getIntent();
        String venueID = intent.getStringExtra(UserActivity.VENUE);
        System.out.println("The venue name is " + venueID);
        myAdapter = new EventListAdapter(this,list, p.getString("uID", "N/A"), venueID);
        recyclerView.setAdapter(myAdapter);
        database.child(venueID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.child("eventList").getChildren()){ //adding all events to the list
                    System.out.println("The vfdsa");
                    Event event = dataSnapshot.getValue(Event.class);
                    if (event.eventApproved == true){
                        list.add(event);
                    }
                }
                myAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        back = (TextView)findViewById(R.id.event_page_back);
        back.setOnClickListener((View.OnClickListener) this);


    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.event_page_back:
                startActivity(new Intent(this, UserActivity.class));
                break;
        }
    }
}
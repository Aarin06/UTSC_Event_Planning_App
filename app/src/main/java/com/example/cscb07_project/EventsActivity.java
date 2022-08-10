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

//    private Spinner event_selector;
//    private Button join_event;

    private TextView back;
    private DatabaseReference ref;

//    private String event_selected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

//        readData(); // read all events from data base
//        event_selector = (Spinner)findViewById(R.id.event_selector);
//        event_selector.setOnItemSelectedListener(this);
//        ArrayAdapter<Event> adapter = new ArrayAdapter<Event>(this, android.R.layout.simple_spinner_item, events_list);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        event_selector.setAdapter(adapter);

//        join_event = (Button)findViewById(R.id.join_event);
//        join_event.setOnClickListener(this);

        recyclerView = findViewById(R.id.event_list);
        database = FirebaseDatabase.getInstance().getReference("Venues");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        SharedPreferences p = getSharedPreferences("myprefs", Context.MODE_PRIVATE);
        Intent intent = getIntent();
        String venueID = intent.getStringExtra(UserActivity.VENUE);
        myAdapter = new EventListAdapter(this,list, p.getString("uID", "N/A"), venueID);
        recyclerView.setAdapter(myAdapter);
        database.child(venueID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.child("eventList").getChildren()){ //adding all events to the list
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
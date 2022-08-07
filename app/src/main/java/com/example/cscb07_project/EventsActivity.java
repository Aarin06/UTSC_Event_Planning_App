package com.example.cscb07_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
        myAdapter = new EventListAdapter(this,list);
        recyclerView.setAdapter(myAdapter);
// add venue once passed in !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        Intent intent = getIntent();
        String venue_name = intent.getStringExtra(UserActivity.VENUE);
        System.out.println("lmao " + venue_name);
        database.child(venue_name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.child("eventsList").getChildren()){ //adding all events to the list
                    Event event = dataSnapshot.getValue(Event.class);
                    System.out.println("lmao " + event.getEnrolledPlayers().get(0)); //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                    list.add(event);
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
                startActivity(new Intent(this, MainActivity.class));
                break;
//            case R.id.join_event:
//                joinEvent();
//                readData();
//                break;
        }
    }

//    private void readData(){
//        ref = FirebaseDatabase.getInstance().getReference("Venue"); // get reference to Venue node
//        // Here we need to pass in the venue selected so that it can show all events within this venue. For now it is Pan am
//        ref.child("Pan am").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Event event = snapshot.getValue(Event.class);
//                    events_list.add(event);
//                    System.out.println(event.eventID);
//                    System.out.println(event.startTime);
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(EventsActivity.this, "Failed to read data", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }

//    @Override
//    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//        event_selected = adapterView.getItemAtPosition(i).toString();
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> adapterView) {
//        join_event.setError("Please select an event from the list below");
//    }
}
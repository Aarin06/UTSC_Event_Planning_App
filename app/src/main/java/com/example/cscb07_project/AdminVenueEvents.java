package com.example.cscb07_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminVenueEvents extends AppCompatActivity {

    String venueID;
    String venueName;
    private RecyclerView recyclerView;
    private DatabaseReference ref;
    private ArrayList<Event> eventArrayList = new ArrayList<>();
    private static final String TAG = "AdminVenueEvents";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_venue);
        //Set up approve events button
        Button approveButton = (Button) findViewById(R.id.button3);
        approveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent i = new Intent(AdminVenueEvents.this, ApproveEvents.class);
                i.putExtra("venue", venueID);

                startActivity(i);

            }
        });

        //Handling DisplayingEvents:
        //Retrieve venue passed by id from AdminScreen
        Intent intent = getIntent();
        venueID = intent.getStringExtra("venue");
        venueName = intent.getStringExtra("venueName");
        //Title of page: Venue's name
        TextView tv = findViewById(R.id.textView3);
        tv.setText(venueName);
        Log.e(TAG, "Test 1 pass");

        //Set
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        ref = db.getReference("Venues").child(venueID).child("eventList");

        recyclerView = findViewById(R.id.id_idRVEvent);
        recyclerView.setHasFixedSize(true);
        AdminEventAdapter adapter = new AdminEventAdapter(this,eventArrayList);
        recyclerView.setAdapter(adapter);

        //put a listener to update for changes like users joining
        ref.addValueEventListener(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Event event = dataSnapshot.getValue(Event.class);
                    eventArrayList.add(event);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






    }
}
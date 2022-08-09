package com.example.cscb07_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ArrayAdapter;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ApproveEvents extends AppCompatActivity {


    private DatabaseReference fire;
    private String venueName;
    private RecyclerView rv;
    private LinearLayoutManager llm;
    private ApproveEventListAdapter adapater;
    private ArrayList<Event> eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_events);

        Intent intent = getIntent();

        venueName = intent.getStringExtra("venue");
        fire = FirebaseDatabase.getInstance().getReference("Venues").child(venueName).child("eventList");
        eventList = new ArrayList<>();

        fire.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {

            //Set up text input
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {


                for (DataSnapshot ds : task.getResult().getChildren()) {
                    Event curr = ds.getValue(Event.class);

                    if (!curr.eventApproved){ eventList.add(curr);}

                }

                recyclerInit();

            }
        });

    }


    private void recyclerInit(){

        RecyclerView rv = (RecyclerView)findViewById(R.id.toBeApproved);
        llm = new LinearLayoutManager(this);
        llm.setOrientation(RecyclerView.VERTICAL);
        rv.setLayoutManager(llm);
        adapater = new ApproveEventListAdapter(this, eventList, venueName);
        rv.setAdapter(adapater);

    }
}
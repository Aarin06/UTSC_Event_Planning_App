package com.example.cscb07_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collection;

public class AdminScreen extends AppCompatActivity {

    private DatabaseReference fire;
    private String[] venueNames;


    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_screen);

        fire = FirebaseDatabase.getInstance().getReference("Venues");



        //Add list of venues as selectable options
        fire.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                venueNames = new String[(int)task.getResult().getChildrenCount()];

                int i = 0; //Scuffed as fuck oops

                for (DataSnapshot ds: task.getResult().getChildren()){
                    venueNames[i++] = ds.getKey();
                }

                AutoCompleteTextView venueSearch = findViewById(R.id.autoCompleteTextView);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(AdminScreen.this, android.R.layout.simple_list_item_1, venueNames);

                venueSearch.setThreshold(1);
                venueSearch.setAdapter(adapter);


            }
        });





    }
}


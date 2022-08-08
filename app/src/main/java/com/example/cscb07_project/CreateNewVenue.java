package com.example.cscb07_project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.cscb07_project.Event;
import com.example.cscb07_project.R;
import com.example.cscb07_project.Venue;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CreateNewVenue extends AppCompatActivity {

    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = db.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_venue);

        EditText etLocation = (EditText) findViewById (R.id.id_etLocation);
        EditText etName = (EditText) findViewById(R.id.id_etName);
        EditText etTime = (EditText) findViewById(R.id.id_edStartTime);
        getTime(etTime);

        Button btSportsOffered = (Button) findViewById(R.id.id_btnAddSport);
        EditText etSport = (EditText) findViewById(R.id.id_etEnterSport);
        //Dynamically generating layout
        btSportsOffered.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutinflater = getLayoutInflater(); //test1
                View v = layoutinflater.inflate(R.layout.activity_create_new_venue, null);

                //Fill in details:
                TextView textViewDisplay = (TextView) findViewById(R.id.id_displaySportsOffered);
                textViewDisplay.setText(sportsOffered.);

            }
        });

        String creatorID = getSharedPreferences("myprefs", MODE_PRIVATE).getString("uid", null);  //SharedPreferences sp = getApplicationContext().getSharedPreferences("myprefs", Context.MODE_PRIVATE);
        String location = etLocation.toString();
        String name = etName.toString();
        String openTime = etTime.toString();
        ArrayList<String> sportsOffered = new ArrayList<String>();
        String venueID;
        ArrayList<Event> eventsList = new ArrayList<Event> ();
        Button submit = (Button) findViewById(R.id.id_submitVenueButton);




        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean successful = true;

                if(location.isEmpty()){
                    etLocation.setError("This field is required");
                    successful = false;
                }
                if (name.isEmpty()) {
                    etName.setError("This field is required");
                    successful = false;
                }
                if (openTime.isEmpty()){
                    etTime.setError("This field is required");
                    successful = false;
                }

                if (!successful)
                    return;
                //else:
                Venue v = new Venue(eventsList);
            }
        });

    }

    public void getTime(EditText etTime){
        etTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                TimePickerDialog timepicker = new TimePickerDialog(CreateNewVenue.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minutes) {
                        etTime.setText(hour + ":" + minutes);
                    }
                }, 0, 0, false);
                timepicker.show();
            }
        });
    }
}
package com.example.cscb07_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cscb07_project.databinding.ActivityCreateNewEventBinding;
import com.example.cscb07_project.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CreateNewEvent extends AppCompatActivity {
    public static final String VENUE = "com.example.cscb07_project.VENUE_ID";
    ActivityCreateNewEventBinding binding;
    DatabaseReference databaseReference;
    FirebaseDatabase db;

    String eventID, creatorID,eventName,startTime,endTime,date,month;
    int maxPlayers, numPlayers,day,year;
    ArrayList<String> enrolledPlayers;

    Button eventButton;
    EditText eName,maxPlay,days,years;
    TextView StartTag,EndTag;
    Spinner sHour,sMin,sAmPm,eHour,eMin,eAmPm,months;
    int currentMonths;
    String currentSHours,currentSMin,currentSAmPm,currentEHours,currentEMin,currentEAmPm;

    String strHours[] = {"Select","1","2","3","4","5","6","7","8","9","10","11","12"};
    String strMins[] = new String[61];
    String ampm[] = {"Select","AM","PM"};
    String monthsArr[] = {"Month","January","February","March","April","May","June","July","August","September","October","November","December"};
    String venueID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_event);
        //Get Venue from previous Intent
        Intent intent = getIntent();
        venueID = intent.getStringExtra(VENUE);

        //Get User ID
        SharedPreferences sp = getApplicationContext().getSharedPreferences("myprefs", Context.MODE_PRIVATE);
        creatorID = sp.getString("uID","");

        setUpSpinners();

        //Setup Text edits
        eName = (EditText)findViewById(R.id.eventName);
        maxPlay = (EditText)findViewById(R.id.maxPlay);
        days = (EditText)findViewById(R.id.day);
        years = (EditText)findViewById(R.id.year);
        StartTag = (TextView)findViewById(R.id.StartTag);


    }
    public void scheduled(){
        startActivity(new Intent(this,ViewUserEventsActivity.class));
    }

    public void callScheduledEventsScreen(View view){

        if (eName.getText().toString().equals("")){
            eName.setError("Event Name Required");
            return;
        }
        if(maxPlay.getText().toString().equals("")){
            maxPlay.setError("Max Players Required");
            return;
        }
        if (days.getText().toString().equals("")){
            days.setError("Day Required");
            return;
        }
        if (years.getText().toString().equals("")){
            years.setError("Year Required");
            return;
        }
        if(currentMonths == 0){
            //toast
            return;
        }
        if (currentEHours.equals("Select") ||currentSHours.equals("Select") ||currentSMin.equals("Select") ||currentEMin.equals("Select") ||currentEAmPm.equals("Select") ||currentSAmPm.equals("Select")){
            //toast
            return;
        }

        eventName = eName.getText().toString();
        maxPlayers = Integer.parseInt(maxPlay.getText().toString());
        day = Integer.parseInt(days.getText().toString());
        year = Integer.parseInt(years.getText().toString());


        if(!validateDate() | !validatePlayers()){
            return;
        }
        date = monthsArr[currentMonths] + "_" + String.valueOf(day) + "_"+ String.valueOf(year);
        startTime = currentSHours+":"+currentSMin+currentSAmPm;
        endTime = currentEHours+":"+currentEMin+currentEAmPm;
        eventID = eventName+"_"+date+"_"+startTime+"_"+endTime;
        enrolledPlayers = new ArrayList<>();
        enrolledPlayers.add(creatorID);
        Event e = new Event(eventID,creatorID,maxPlayers,1,eventName,enrolledPlayers,startTime,endTime,false,date);

        db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("Venues").child(venueID).child("eventList");
        databaseReference.child(eventID).setValue(e).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                System.out.println("Complete");
                databaseReference = db.getReference("Users").child(creatorID).child("eventsCreated");
                databaseReference.child(e.eventID).setValue(e.eventID);
                databaseReference = db.getReference("Users").child(creatorID).child("eventsJoined");
                databaseReference.child(e.eventID).setValue(e.eventID);
               // scheduled();
            }
        });

    }

    private boolean validatePlayers(){
        if (maxPlayers <= 0){
            maxPlay.setError("Must be greater than 0");
            return false;
        }
        return true;
    }

    private boolean validateDate(){
        if(currentMonths == 0){
            return false;
        }
        if (currentMonths == 1 || currentMonths == 3 || currentMonths == 5 || currentMonths == 7 || currentMonths == 8 || currentMonths == 10 || currentMonths == 12 ){
            //31 days
            if (day > 31 || day < 1){
                days.setError("Enter valid day");
                return false;
            }

        }
        else if (currentMonths == 2){
            //28 days
            if (day > 28 || day < 1){
                days.setError("Enter valid day");
                return false;
            }
        }
        else{
            if (day > 30 || day < 1){
                days.setError("Enter valid day");
                return false;
            }
        }
        int tempYear = year-1900;
        if (tempYear <=0){
            years.setError("Enter valid year");
            return false;
        }

        Calendar cal = Calendar.getInstance();
        Date today = cal.getTime();
        int finalSHours,finalEHours;

        if (currentSAmPm.equals("AM")){
            finalSHours = Integer.parseInt(currentSHours);
        }
        else if (currentSAmPm.equals("PM")){
            finalSHours = Integer.parseInt(currentSHours)+12;
        }
        else{

            return false;
        }

        if (currentEAmPm.equals("AM")){
            finalEHours = Integer.parseInt(currentEHours);
        }
        else if (currentSAmPm.equals("PM")){
            finalEHours = Integer.parseInt(currentEHours)+12;
        }
        else{
            return false;
        }

        Date start = new Date(tempYear,currentMonths+1,day,finalSHours,Integer.parseInt(currentSMin));
        Date end = new Date(tempYear,currentMonths+1,day,finalEHours,Integer.parseInt(currentEMin));

        if (start.getTime() - today.getTime() < 0){
            years.setError("This date and time has passed");
            return false;
        }
        System.out.println("StartTime: "+start.getTime()+"\t\tEndTime: "+end.getTime());
        System.out.println(end.getTime()-start.getTime());
        System.out.println(start);
        System.out.println(end);

        if (end.getTime()-start.getTime() < 1800000){

            years.setError("Session must be atleast 30 minutes.");
            return false;
        }
        if (end.getTime()-start.getTime() > 7200000){

            years.setError("Session must be at most 2 hours.");
            return false;
        }

        return true;
    }

//    public void validateEvent(){
//        db = FirebaseDatabase.getInstance();
//        databaseReference = db.getReference("Venues").child(venueID).child("eventList");
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

    private void setUpSpinners(){

        strMins[0] = "Select";
        for (int i = 1 ; i <= 60 ; i++){
            if (i <= 9) {
                strMins[i] = "0"+String.valueOf(i-1);
            }
            else{
                strMins[i] = String.valueOf(i);
            }


        }
        //Set Up Spinners
        sHour = findViewById(R.id.sHour);
        sHour.setAdapter(new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_dropdown_item,strHours));
        sHour.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currentSHours = sHour.getItemAtPosition(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        sMin = findViewById(R.id.sMin);
        sMin.setAdapter(new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_dropdown_item,strMins));
        sMin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currentSMin = sMin.getItemAtPosition(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        sAmPm = findViewById(R.id.sAmPm);
        sAmPm.setAdapter(new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_dropdown_item,ampm));
        sAmPm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currentSAmPm = sAmPm.getItemAtPosition(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        eHour = findViewById(R.id.eHour);
        eHour.setAdapter(new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_dropdown_item,strHours));
        eHour.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currentEHours = eHour.getItemAtPosition(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        eMin = findViewById(R.id.eMin);
        eMin.setAdapter(new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_dropdown_item,strMins));
        eMin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currentEMin = eMin.getItemAtPosition(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        eAmPm = findViewById(R.id.eAmPm);
        eAmPm.setAdapter(new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_dropdown_item,ampm));
        eAmPm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currentEAmPm = eAmPm.getItemAtPosition(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        months = findViewById(R.id.month);
        months.setAdapter(new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_dropdown_item,monthsArr));
        months.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currentMonths = i;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }


}
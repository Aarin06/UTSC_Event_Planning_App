package com.example.cscb07_project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CreateNewVenue extends AppCompatActivity {

    FirebaseDatabase db;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Default
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_venue);

        //Create the new Venue object // Communicate with the Activity and get inputs
        //Regarding the activity xml, handle getting the correct values
        EditText etLocation = (EditText) findViewById (R.id.id_etLocation);
        EditText etName = (EditText) findViewById(R.id.id_etName);
        EditText etTime = (EditText) findViewById(R.id.id_edStartTime);
        //getTime(etTime);
        etTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                TimePickerDialog timepicker = new TimePickerDialog(CreateNewVenue.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minutes) {
                        System.out.println("should be first");
                        String ending;
                        String strHour;
                        String strMinutes;
                        if (minutes < 10)
                            strMinutes = "0" + minutes;
                        else
                            strMinutes = "" + minutes;

                        if (hour > 12) {
                            strHour = (hour - 12) + "";
                            ending = "PM";
                        } else {
                            if (hour == 0){
                                strHour = "12";
                            } else
                                strHour = "" + hour;
                            ending = "AM";
                        }
                        etTime.setText(strHour + ":" + strMinutes + ending);
                    }
                }, 0, 0, false);
                timepicker.show();
            }
        });

        Button btSportsOffered = (Button) findViewById(R.id.id_btnAddSport);
        EditText etSport = (EditText) findViewById(R.id.id_etEnterSport);
        TextView textViewDisplay = (TextView) findViewById(R.id.id_displaySportsOffered);
        ArrayList<String> sportsOffered = new ArrayList<String>();
        ArrayList<Event> eventsList = new ArrayList<Event> ();
        //Modifying layout for sports
        //Clicking button should add one sport to the list, a newline in the display.
        btSportsOffered.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etSport.toString().trim().isEmpty()){
                    etSport.setError("Sport name must not be empty"); // not showing
                    return;
                }

                //Else: Fill in details:
                String currentSport = etSport.getText().toString();
                //Increase size of the view showing Sports Offered ; obsolete, replaced with multilinetextview https://stackoverflow.com/questions/27698139/how-to-make-android-edittext-expand-vertically-when-full
//                int oldHeight = textViewDisplay.getLayoutParams().height;
//                ViewGroup.LayoutParams params = textViewDisplay.getLayoutParams();
//                params.width = oldHeight+20;
//               textViewDisplay.setLayoutParams(params);
               //https://stackoverflow.com/questions/9175391/re-setting-a-textview-height-programmatically

                String s = textViewDisplay.getText().toString() + currentSport + "\n";
                textViewDisplay.setText(s);
                etSport.setText("");

                //add to venue.sportsOffered;
                sportsOffered.add(currentSport);
            }
        });


        Button submit = (Button) findViewById(R.id.id_submitVenueButton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean successful = true;

                String creatorID = getSharedPreferences("myprefs", MODE_PRIVATE).getString("uID", null);  //SharedPreferences sp = getApplicationContext().getSharedPreferences("myprefs", Context.MODE_PRIVATE);
                String location = etLocation.getText().toString();
                String name = etName.getText().toString();
                String openTime = etTime.getText().toString();

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
                if (sportsOffered.isEmpty()) {
                    etSport.setError("This field is required");
                    successful = false;
                }

                if (!successful)
                    return;
                else{
                    Venue v = new Venue(creatorID, location, name, openTime, sportsOffered, "0", eventsList);
                    passToFirebase(v);
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), AdminScreen.class);
                    startActivity(intent);
                }
            }
        });
    }
    public void passToFirebase(Venue venue){
    //Preparation of Firebase
        db = FirebaseDatabase.getInstance();
        ref = db.getReference();
        DatabaseReference refVenues = ref.child("Venues");

    //Pass the Venue object.
        String id = refVenues.push().getKey();
        refVenues.child(id).setValue(venue);
        //on firebase, add the id as field
        refVenues.child(id).child("venueID").setValue(id);

    }
    public void getTime(EditText etTime){
        etTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                TimePickerDialog timepicker = new TimePickerDialog(CreateNewVenue.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minutes) {
                        System.out.println("should be first");
                        String ending;
                        String strHour;
                        String strMinutes;
                        if (minutes < 10)
                            strMinutes = "0" + minutes;
                        else
                            strMinutes = "" + minutes;

                        if (hour > 12) {
                            strHour = (hour - 12) + "";
                            ending = "PM";
                        } else {
                            if (hour == 0){
                                strHour = "12";
                            } else
                                strHour = "" + hour;
                            ending = "AM";
                        }
                        etTime.setText(strHour + ":" + strMinutes + ending);
                    }
                }, 0, 0, false);
                timepicker.show();
            }
        });
    }
}
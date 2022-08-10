package com.example.cscb07_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView registration;
    private EditText emailField, passwordField;
    private Button login;
    private FirebaseAuth fire;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registration = (TextView)findViewById(R.id.registration);
        registration.setOnClickListener(this);
        emailField = (EditText)findViewById(R.id.email);
        passwordField = (EditText)findViewById(R.id.password);
        login = (Button)findViewById(R.id.login);
        login.setOnClickListener(this);
        fire = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.registration:
                startActivity(new Intent(this, Registration.class));
                break;
            case R.id.login:
                login();
                break;
        }
    }

    public void startUserActivity(){
        startActivity(new Intent(this,UserActivity.class));
    }

    private void login() {
        String email = emailField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();
        if (email.equals("") || password.equals("") || password.length() < 6 || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            if (email.equals(""))
                emailField.setError("This field is required.");
            else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                emailField.setError("Invalid email.");
            if (password.equals(""))
                passwordField.setError("This field is required.");
            else if (password.length() < 6)
                passwordField.setError("Password must be at least six characters.");
            return;
        }
        fire.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String uID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    FirebaseDatabase.getInstance().getReference().child("Users").child(uID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (task.isSuccessful()) {
                                String data = String.valueOf(task.getResult().child("status").getValue());
                                int status = Integer.parseInt(data);
                                SharedPreferences p = getSharedPreferences("myprefs", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = p.edit();
                                editor.putString("uID", uID);
                                editor.apply();
                                if (status == 0) {
                                    //To whoever is dealing with the user end of things, replace the line below
                                    //and redirect to a new activity.
                                    startUserActivity();
                                    Toast.makeText(MainActivity.this, "Logged in as user!", Toast.LENGTH_LONG).show();
                                } else {
                                    //To whoever is dealing with the admin end of things, replace the line below
                                    //and redirect to a new activity.
                                    //Toast.makeText(MainActivity.this, "Logged in as admin!", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(MainActivity.this, AdminScreen.class));
                                }
                            } else {
                                //This should not happen.
                            }
                        }
                    });
                } else {
                    Toast.makeText(MainActivity.this, "Invalid credentials.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void eventsPage() {
        startActivity(new Intent(this, EventsActivity.class));
    }
}
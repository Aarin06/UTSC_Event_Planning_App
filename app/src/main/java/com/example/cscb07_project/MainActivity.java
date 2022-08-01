package com.example.cscb07_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
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

    private void login() {
        String email = emailField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();
        boolean emailEmpty = false, passwordEmpty= false;
        if (email.equals(""))
            emailEmpty = true;
        if (password.equals(""))
            passwordEmpty = true;
        if (emailEmpty || passwordEmpty) {
            if (emailEmpty)
                emailField.setError("This field is required.");
            if (passwordEmpty)
                passwordField.setError("This field is required.");
            return;
        }
        if (password.length() < 6) {
            passwordField.setError("Password must be at least six characters.");
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailField.setError("Invalid email.");
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
                                String data = String.valueOf(task.getResult().getValue()).trim();
                                int status = Integer.parseInt(data.substring(data.length() - 2, data.length() - 1));
                                if (status == 0) {
                                    //To whoever is dealing with the user end of things, replace the line below
                                    //and redirect to a new activity.
                                    //Toast.makeText(MainActivity.this, "Logged in as user!", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(MainActivity.this, ViewUserEventsActivity.class));
                                } else {
                                    //To whoever is dealing with the admin end of things, replace the line below
                                    //and redirect to a new activity.
                                    Toast.makeText(MainActivity.this, "Logged in as admin!", Toast.LENGTH_LONG).show();
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
}
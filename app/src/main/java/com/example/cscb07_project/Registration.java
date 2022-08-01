package com.example.cscb07_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth fire;
    private EditText nameField, emailField, passwordField;
    private TextView back;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        fire = FirebaseAuth.getInstance();
        nameField = (EditText)findViewById(R.id.name);
        emailField = (EditText)findViewById(R.id.email);
        passwordField = (EditText)findViewById(R.id.password);
        back = (TextView)findViewById(R.id.back);
        back.setOnClickListener(this);
        register = (Button)findViewById(R.id.register);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.register:
                register();
                break;
        }
    }

    private void register() {
        String name = nameField.getText().toString().trim();
        String email = emailField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();
        if (name.equals("") || email.equals("") || password.equals("") || password.length() < 6 || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            if (name.equals(""))
                nameField.setError("This field is required.");
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
        fire.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        String uID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        User user = new User(name, email, uID);
                        FirebaseDatabase.getInstance().getReference("Users").child(
                                FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Registration.this, "Registration Successful!", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(Registration.this, MainActivity.class));
                                } else {
                                    Toast.makeText(Registration.this, "Registration Unsuccessful.", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(Registration.this, "Registration Unsuccessful.", Toast.LENGTH_LONG).show();
                    }
                }
            });
    }
}
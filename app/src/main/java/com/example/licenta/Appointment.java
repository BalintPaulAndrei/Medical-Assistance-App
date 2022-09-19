package com.example.licenta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Appointment extends AppCompatActivity {

    private Button schedule, backButton;
    private EditText editTextName, editTextDate, editTextHour, editTextEmail;
    private ProgressBar progressBar;

    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root = db.getReference().child("Schedule appointment");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        editTextName = findViewById(R.id.name);
        editTextDate = findViewById(R.id.date);
        editTextHour = findViewById(R.id.time);
        editTextEmail = findViewById(R.id.email);
        schedule = findViewById(R.id.schedule);
        backButton = findViewById(R.id.back);
        progressBar = findViewById(R.id.progressBar);

        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = editTextName.getText().toString();
                String date = editTextDate.getText().toString();
                String time = editTextHour.getText().toString();
                String email = editTextEmail.getText().toString();

                if(name.isEmpty()){
                    editTextName.setError("Full name is required!");
                    editTextName.requestFocus();
                    return;
                }
                if(date.isEmpty()){
                    editTextDate.setError("Date is required!");
                    editTextDate.requestFocus();
                    return;
                }
                if(time.isEmpty()){
                    editTextHour.setError("Time is required!");
                    editTextHour.requestFocus();
                    return;
                }
                if(email.isEmpty()){
                    editTextEmail.setError("Email is required!");
                    editTextEmail.requestFocus();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                HashMap<String, String> userMap = new HashMap<>();

                userMap.put("name", name);
                userMap.put("date", date);
                userMap.put("time", time);
                userMap.put("email", email);


                if(!name.isEmpty() && !date.isEmpty() && !time.isEmpty() && !email.isEmpty()){
                    root.push().setValue(userMap);
                    Toast.makeText(Appointment.this, R.string.success_label1, Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }else{
                    Toast.makeText(Appointment.this, R.string.error_label, Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Appointment.this, WelcomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
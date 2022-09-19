package com.example.licenta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Ambulance extends AppCompatActivity {

    private Button schedule, backButton;
    private EditText editTextName, editTextDate, editTextHour, editTextLocation;
    private ProgressBar progressBar;

    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root = db.getReference().child("Schedule ambulance");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance);

        editTextName = findViewById(R.id.name);
        editTextDate = findViewById(R.id.date);
        editTextHour = findViewById(R.id.time);
        editTextLocation = findViewById(R.id.location);
        schedule = findViewById(R.id.schedule);
        backButton = findViewById(R.id.back);
        progressBar = findViewById(R.id.progressBar);

        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = editTextName.getText().toString();
                String date = editTextDate.getText().toString();
                String time = editTextHour.getText().toString();
                String location = editTextLocation.getText().toString();

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
                if(location.isEmpty()){
                    editTextLocation.setError("Location is required!");
                    editTextLocation.requestFocus();
                    return;
                }

                HashMap<String, String> userMap = new HashMap<>();

                userMap.put("Name", name);
                userMap.put("Date", date);
                userMap.put("Time", time);
                userMap.put("Location", location);

                progressBar.setVisibility(View.VISIBLE);

                if(!name.isEmpty() && !date.isEmpty() && !time.isEmpty() && !location.isEmpty()){
                    root.push().setValue(userMap);
                    Toast.makeText(Ambulance.this, R.string.success_label, Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }else{
                    Toast.makeText(Ambulance.this, R.string.error_label, Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Ambulance.this, WelcomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
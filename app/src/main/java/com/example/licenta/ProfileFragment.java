package com.example.licenta;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {

    private FirebaseUser user;
    private DatabaseReference reference;
    private TextView fullNameText, emailText, ageText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);

        fullNameText = view.findViewById(R.id.fullName);
        emailText = view.findViewById(R.id.emailAddress);
        ageText = view.findViewById(R.id.age);
        user = FirebaseAuth.getInstance().getCurrentUser();

        reference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null){
                    String fullName = userProfile.fullName;
                    String email = userProfile.email;
                    String age = userProfile.age;

                    fullNameText.setText(fullName);
                    emailText.setText(email);
                    ageText.setText(age);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}



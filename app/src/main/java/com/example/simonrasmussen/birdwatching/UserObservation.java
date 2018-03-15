package com.example.simonrasmussen.birdwatching;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

public class UserObservation extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private TextView welcomeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_observation);
        TextView welcomeText = findViewById(R.id.welcomeText);
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if (bd != null) {
            Object user = bd.get("email");
            welcomeText.setText(user.toString());
            Log.d("JENS", "onCreate: " + user.toString()

            );
        }
    }
}

package com.example.simonrasmussen.birdwatching;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import java.util.HashMap;

public class CreateObservation extends AppCompatActivity {
    private HashMap<String, String> hashMap;
    private EditText editDanishName;
    private EditText editEnglishName;
    private EditText editLongitude;
    private EditText editLatitude;
    private EditText editInputDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_observation);
        editDanishName = findViewById(R.id.danishName);
        editEnglishName = findViewById(R.id.englishName);
        editLongitude = findViewById(R.id.longitude);
        editLatitude = findViewById(R.id.latitude);
        editInputDate = findViewById(R.id.inputDate);
        Intent intent = getIntent();
        hashMap = (HashMap<String, String>) intent.getSerializableExtra("bird");
        editDanishName.setText(hashMap.get("DanishName"));
        editEnglishName.setText(hashMap.get("EnglishName"));
        editLongitude.setText("GET DATA FROM GPS");
        editLatitude.setText("GET DATA FROM GPS");
        Log.d("xd", "onCreate: "+ hashMap.get("DanishName"));
    }
}

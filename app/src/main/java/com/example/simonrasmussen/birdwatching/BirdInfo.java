package com.example.simonrasmussen.birdwatching;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.HashMap;

public class BirdInfo extends AppCompatActivity {
//
    //
    //
    //          https://stackoverflow.com/questions/7578236/how-to-send-hashmap-value-to-another-activity-using-an-intent?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
    //
    //
    private String url = "";
    private Button createBtn;
    private HashMap<String, String> hashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createBtn = (Button) findViewById(R.id.bird_info_createbtn);
        setContentView(R.layout.activity_bird_info);
        createBtn = findViewById(R.id.bird_info_createbtn);
        createBtn.setOnClickListener(buttonClickListener);
        Intent intent = getIntent();
        hashMap = (HashMap<String, String>) intent.getSerializableExtra("bird");
        Log.v("HashMapTest", hashMap.get("PhotoUrl"));
        url = hashMap.get("PhotoUrl");
        ImageView imageView = (ImageView) findViewById(R.id.birdImage);

        Glide.with(this).load(url).into(imageView);
    }
        private View.OnClickListener buttonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (view.getId())

                {
                    case R.id.bird_info_createbtn:
                        Log.d("Hello", "CLICKED" + hashMap.get("DanishName"));
                        Intent intent = new Intent(BirdInfo.this, CreateObservation.class);
                        intent.putExtra("bird", hashMap );
                        startActivity(intent);
                        break;
                }

            }
        };
    }

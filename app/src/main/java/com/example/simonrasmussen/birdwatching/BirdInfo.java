package com.example.simonrasmussen.birdwatching;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bird_info);
        Intent intent = getIntent();
        HashMap<String, String> hashMap = (HashMap<String, String>)intent.getSerializableExtra("bird");
        Log.v("HashMapTest", hashMap.get("PhotoUrl"));
        url = hashMap.get("PhotoUrl");
        ImageView imageView = (ImageView) findViewById(R.id.birdImage);

        Glide.with(this).load(url).into(imageView);
    }
}

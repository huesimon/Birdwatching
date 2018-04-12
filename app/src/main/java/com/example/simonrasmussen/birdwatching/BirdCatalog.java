package com.example.simonrasmussen.birdwatching;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.simonrasmussen.birdwatching.DAO.HttpHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class BirdCatalog extends AppCompatActivity {


    private String TAG = MainActivity.class.getSimpleName();
    private ListView lv;

    ArrayList<HashMap<String, String>> birdList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bird_catalog);

        birdList = new ArrayList<>();
        lv = findViewById(R.id.list);

        new GetBrids().execute();
    }

    private class GetBrids extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(BirdCatalog.this,"Json Data is downloading",Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "http://birdobservationservice.azurewebsites.net/Service1.svc/birds";
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONArray birds = new JSONArray(jsonStr);
                    Log.d(TAG, "doInBackground: " + birds.length());

                    // looping through All birds
                    for (int i = 0; i < birds.length(); i++) {
                        JSONObject c = birds.getJSONObject(i);
                        String id = c.getString("Id");
                        String danishName = c.getString("NameDanish");
                        String englishName = c.getString("NameEnglish");




                        // tmp hash map for single bird
                        HashMap<String, String> bird = new HashMap<>();

                        // adding each child node to HashMap key => value
                        bird.put("id", id);
                        bird.put("DanishName", danishName);
                        bird.put("EnglishName", englishName );


                        // adding bird to bird list
                        birdList.add(bird);
                        Log.d(TAG, "doInBackground: "+ bird);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            ListAdapter adapter = new SimpleAdapter(BirdCatalog.this, birdList,
                    R.layout.row, new String[]{ "id","DanishName","EnglishName"},
                    new int[]{R.id.id, R.id.DanishName, R.id.EnglishName});
            lv.setAdapter(adapter);
        }
    }
    }
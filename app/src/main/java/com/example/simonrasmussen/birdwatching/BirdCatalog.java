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

    ArrayList<HashMap<String, String>> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bird_catalog);

        contactList = new ArrayList<>();
        lv = findViewById(R.id.list);

        new GetContacts().execute();
    }

    private class GetContacts extends AsyncTask<Void, Void, Void> {
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

                    // looping through All Contacts
                    for (int i = 0; i < birds.length(); i++) {
                        JSONObject c = birds.getJSONObject(i);
                        String id = c.getString("Id");
                        String danishName = c.getString("NameDanish");
                        String englishName = c.getString("EnglishName");




                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value
                        contact.put("name", id);
                        contact.put("email", danishName);
                        contact.put("mobile", englishName );


                        // adding contact to contact list
                        contactList.add(contact);
                        Log.d(TAG, "doInBackground: "+ contact);
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
            ListAdapter adapter = new SimpleAdapter(BirdCatalog.this, contactList,
                    R.layout.row, new String[]{ "email","mobile","name"},
                    new int[]{R.id.email, R.id.mobile, R.id.name});
            lv.setAdapter(adapter);
        }
    }
    }
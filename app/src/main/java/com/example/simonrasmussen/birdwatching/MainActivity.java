package com.example.simonrasmussen.birdwatching;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private String name = "";
    private String email = "";
    private String password = "";
    //variables
    private EditText mailText;
    private EditText nameText;
    private EditText passText;
    private Button loginBtn;
    private Button signupBtn;
    public static final String TAG = "MainActivity";

    // Firebase stuff
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private String userID;
    private FirebaseUser fbUser;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        // Check if Firebase is already logged in to firebase
        if (firebaseAuth.getCurrentUser() != null) {
            // The Firebase is already logged in to
            Log.d(TAG, "onCreate: " + firebaseAuth.getCurrentUser().getEmail());

            //TODO:Intent intent = new Intent(MainActivity.this, UserObservation.class);
            Intent intent = new Intent(MainActivity.this, BirdCatalog.class);
            intent.putExtra("email", firebaseAuth.getCurrentUser().getEmail());
            startActivity(intent);

        }
        progressDialog = new ProgressDialog(this);


        //
        //
        // Everything here is from app_bar class -----------------

        EditText searchBar = (EditText) findViewById(R.id.searchBar);
        TextView titleBar = (TextView) findViewById(R.id.titleBar);


        titleBar.setText("Title");

        //
        //

        nameText = (EditText) findViewById(R.id.main_nameText);
        mailText = (EditText) findViewById(R.id.main_emailText);
        passText = (EditText) findViewById(R.id.main_passwordText);
        loginBtn = findViewById(R.id.main_loginbtn);
        signupBtn = findViewById(R.id.main_signupbtn);
        loginBtn.setOnClickListener(buttonClickListener);
        signupBtn.setOnClickListener(buttonClickListener);
    }

    private void userLogin() {
        name = nameText.getText().toString().trim();
        email = mailText.getText().toString().trim();
        password = passText.getText().toString().trim();

        if ((TextUtils.isEmpty(email)) || (TextUtils.isEmpty(password))) {
            // Email or Password empty
            Toast.makeText(MainActivity.this, "Please enter valid values in text fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // If EditText not empty it comes to here
        progressDialog.setMessage("Checking user information...");
        progressDialog.show();


        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            // The login is successful
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            //toastMessage("User login successful");
                            Toast.makeText(MainActivity.this, "Login succesful - Checker Info", Toast.LENGTH_SHORT).show();


                            //User id, will hopefully be used to search for their own posts in another activity
                            Log.d("Login", "onComplete: " + user.getUid());


                            //TODO:Intent intent = new Intent(MainActivity.this, UserObservation.class);
                            Intent intent = new Intent(MainActivity.this, BirdCatalog.class);
                            //Used to show the email in the next Activity (just for POC)
                            User createdUser = new User(email);
                            intent.putExtra("email", createdUser.getEmail());
                            startActivity(intent);
                        } else {

                            Toast.makeText(MainActivity.this, "User not found,", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void userSignup() {
        email = mailText.getText().toString().trim();
        password = passText.getText().toString().trim();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {


                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: ");
                            createdUser(name , email);
                            finish();
                            startActivity(new Intent(MainActivity.this, MainActivity.class));
                        } else {
                            // Not successful
                        }
                    }
                });


    }


    public void createdUser(String name, String email) {
        //Used to upload to firebase
        FirebaseUser fbUser = firebaseAuth.getCurrentUser();
        String userID = fbUser.getUid();
        User user = new User(name, email,userID);

        //Child = folder, so root/users/userOBJ/email,name and so on
        databaseReference.child("users").child(userID).setValue(user);
        Log.d(TAG, "createdUser: " + user) ;
    }

    private View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId())

            {
                case R.id.main_loginbtn:
                    Log.d("Click", "onClick: login");
                    userLogin();
                    //Toast.makeText(LoginActivity.this, "You are trying to Login...", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.main_signupbtn:
                    Log.d(TAG, "onClick: signup");
                    userSignup();
                    break;
            }
        }
    };
}




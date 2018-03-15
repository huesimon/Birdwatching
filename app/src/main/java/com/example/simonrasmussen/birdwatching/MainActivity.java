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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private String email = "";
    private String password = "";
    //variables
    private EditText mailText;
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
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        // Check if Firebase is already logged in to
        if (firebaseAuth.getCurrentUser() != null) {
            // The Firebase is already logged in to

        }
        progressDialog = new ProgressDialog(this);


        mailText = (EditText) findViewById(R.id.main_emailText);
        passText = (EditText) findViewById(R.id.main_passwordText);
        loginBtn = findViewById(R.id.main_loginbtn);
        signupBtn = findViewById(R.id.main_signupbtn);
        loginBtn.setOnClickListener(buttonClickListener);
        signupBtn.setOnClickListener(buttonClickListener);
    }

    private void userLogin() {
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


                            Intent intent = new Intent(MainActivity.this, UserObservation.class);
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
                            createdUser("jens", email);
                            finish();
                            startActivity(new Intent(MainActivity.this, MainActivity.class));
                        } else {
                            // Not successful
                        }
                    }
                });


    }


    public void createdUser(String name, String email) {
        FirebaseUser fbUser = firebaseAuth.getCurrentUser();
        String userID = fbUser.getUid();
        User user = new User(email);
        String childInfo = "UserInfo";
        databaseReference.child("users").child(childInfo).setValue(user);
        Log.d(TAG, "createdUser: " + userID);
        firebaseAuth.signOut();
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




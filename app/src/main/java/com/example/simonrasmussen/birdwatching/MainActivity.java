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


    //variables
    private EditText mailText;
    private EditText passText;
    private Button loginBtn;

    // Firebase stuff
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference myUserIDRef;
    private String userID;
    private FirebaseUser fbUser;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        // Check if Firebase is already logged in to
        if (firebaseAuth.getCurrentUser() != null) {
            // The Firebase is already logged in to

        }

        progressDialog = new ProgressDialog(this);


        mailText = (EditText) findViewById(R.id.main_emailText);
        passText = (EditText) findViewById(R.id.main_passwordText);
        loginBtn = findViewById(R.id.main_loginbtn);
        loginBtn.setOnClickListener(buttonClickListener);
    }

    private void userLogin() {
        String email = mailText.getText().toString().trim();
        String pass = passText.getText().toString().trim();

        if ((TextUtils.isEmpty(email)) || (TextUtils.isEmpty(pass))) {
            // Email or Password empty
            Toast.makeText(MainActivity.this, "Please enter valid values in text fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // If EditText not empty it comes to here
        progressDialog.setMessage("Checking user information...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            // The login is successful
                            //toastMessage("User login successful");
                            Toast.makeText(MainActivity.this, "Login succesful - Checker Info", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, UserObservation.class);

                            startActivity(intent);
                        } else {
                            // Not successful
                            Toast.makeText(MainActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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
            }
        }
    };
}




package com.example.project31;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.project31.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;


public class Login extends AppCompatActivity {
    TextInputEditText editTextUsername, editTextPassword;
    Button buttonLogin;

    DatabaseReference database;
    ProgressBar progressBar;
    TextView textView;

    /**
     * Handles application start
     * If CurrentUserData shows a logged in user, takes user to new activity as determined by user role
     * If user role is ADMIN: Moves to Main activity
     * If user role is CLUB or PARTICIPANT: Moves to WelcomeScreen activity
     */
    @Override
    public void onStart() {
        //Executes android standard onStart
        super.onStart();
        //Checks if User is logged in
        if(CurrentUserData.isLoggedIn()) {
            //Sets intent to WelcomeScreen activity
            Intent intent = new Intent(getApplicationContext(), WelcomeScreen.class);
            //If user is an admin, changes intent to Main activity
            if(CurrentUserData.getCurrentUser().getRole() == UserType.ADMIN) {intent = new Intent(getApplicationContext(), MainActivity.class);}
            //Moves to intended activity
            startActivity(intent);
            finish();
        }
    }


    /**
     * Called on creation of activity
     * Initializes reference to database ("Users" path), text fields, buttons, and progress bar
     * username and password text fields used for login data
     * "Login" button checks if user can login, if so, takes them to either Welcome screen activity, or Main activity (ADMIN)
     * "Register now" button takes user to Register activity
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Initializes reference to database ("Users" path)
        database = FirebaseDatabase.getInstance().getReference("Users");
        //Initializes UI elements
        editTextUsername = findViewById(R.id.username);
        editTextPassword = findViewById(R.id.password);
        buttonLogin = findViewById(R.id.btn_login);
        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.registerNow);
        //Listens for clicks on "Register now" button
        textView.setOnClickListener(new View.OnClickListener() {
            //Takes user to Register activity
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
                finish();
            }
        });
        //Listens for clicks on "Login" button
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Pulls text from text boxes
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();
                //Checks if fields are empty, if so, sends message
                if(TextUtils.isEmpty(username)) {
                    Toast.makeText(Login.this,"Please Enter Username",Toast.LENGTH_LONG).show();
                }
                if(TextUtils.isEmpty(password)) {
                    Toast.makeText(Login.this,"Please Enter Password",Toast.LENGTH_LONG).show();
                }
                else {
                    //Gets snapshot of database
                    database.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //Create login authenticator to store validity of fields
                            LoginAuthenticator auth = new LoginAuthenticator();
                            //Create user to login if successful
                            User user = new User(null,null,null,null);
                            //Checks if username exists in system, if does sets username to valid in login authenticator
                            if(snapshot.child(username).exists()) {
                                auth.setUsernameValidTrue();
                            }
                            //If not found, displays message and breaks from method
                            else {
                                Toast.makeText(Login.this,"Username does not exist",Toast.LENGTH_LONG).show();
                                return;
                            }
                            //Calls usernames data, if stored password is equal to inputed password, sets password to valid in login authenticator
                            if(snapshot.child(username).child("password").getValue().equals(password)) {
                                auth.setPasswordValidTrue();
                            }
                            //If not equal, displays message
                            else {
                                Toast.makeText(Login.this,"Incorrect Password",Toast.LENGTH_LONG).show();
                            }
                            //If login authenticator fields all valid
                            if(auth.allValid()) {
                                //Sets user to be logged in's username and password
                                user.setUsername(username);
                                user.setPassword(password);
                                //Gets email from user to be logged in, sets user instance email
                                user.setEmail((String) snapshot.child(username).child("email").getValue());
                                //Gets type from user to be logged in
                                String roleString = (String) snapshot.child(username).child("type").getValue();
                                //Converts string to UserType enum
                                UserType role = UserType.PARTICIPANT;
                                if(roleString.equals("ADMIN")) {role = UserType.ADMIN;}
                                else if(roleString.equals("CLUB")) {role = UserType.CLUB;}
                                //Sets user instances role
                                user.setRole(role);
                                user.setRoleString(roleString);
                                //Sets user instance to logged in user
                                CurrentUserData.setCurrentUser(user);

                                MyApplication.setCurUser(user);
                                //Sets intent to WelcomeScreen activity
                                // Intent intent = new Intent(getApplicationContext(), WelcomeScreen.class);
                                Intent intent = new Intent(getApplicationContext(), Participant.class);
                                //If admin, changes intent to Main activity
                                if(role == UserType.ADMIN) {
                                    intent = new Intent(getApplicationContext(), MainActivity.class);
                                }else if(role == UserType.CLUB){
                                    intent = new Intent(getApplicationContext(), Club.class);
                                }
                                //Starts desired activity
                                startActivity(intent);
                                finish();
                            }
                            //If any fields invalid, shows message
                            else {
                                Toast.makeText(Login.this,"Login Failed",Toast.LENGTH_LONG).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });
                }
            }
        });
    }

    public Login(){

    }

    public static String validateUsername(String username){
        String valid = null;

        if(username != ""){
            valid = "true";
        }else if (username == "admin"){
            valid = "false";
        }

        return valid;
    }
}
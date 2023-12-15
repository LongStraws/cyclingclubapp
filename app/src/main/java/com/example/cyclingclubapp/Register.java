package com.example.cyclingclubapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {

    TextInputEditText editTextEmail, editTextPassword, editTextUsername;
    Button buttonReg;
    ProgressBar progressBar;
    TextView textView;

    Spinner dropDown;

    DatabaseReference database;

    private int position;

    /**
     * Handles starting of application
     */
    public void onStart() {
        super.onStart();
    }

    /**
     * Called on creation of Register activity
     * Implements text fields, buttons, spinner, and a progress bar
     * "Register" button creates user if all fields are valid
     * "Login now" button takes user to Login activity
     * Email, password, and username text boxes used to input user creation data
     * Roles spinner used to select user role data
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //Set reference to database path "Users"
        database = FirebaseDatabase.getInstance().getReference("Users");
        //Initialize all UI elements
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        editTextUsername = findViewById(R.id.username);
        progressBar = findViewById(R.id.progressBar);
        buttonReg = findViewById(R.id.btn_register);
        textView = findViewById(R.id.loginNow);
        dropDown = findViewById(R.id.spinner1);
        dropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Register.this.position = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Set dropdown items
        String[] items = {"Participant", "Club"};
        //Adapt dropdown items array into UI element
        dropDown.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items));
        //Listen for clicks on login now field
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Move to login activity
                Intent intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
                finish();
            }
        });
        buttonReg.setOnClickListener(new View.OnClickListener() {
            //Listen for click on login button
            @Override
            public void onClick(View view) {
                //Get text from text from fields
                String username = editTextUsername.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                //Check if any one of the fields is empty, display message if true
                if(TextUtils.isEmpty(username)) {
                    Toast.makeText(Register.this, "Please Enter Username", Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(password)){
                    Toast.makeText(Register.this, "Please Enter Password", Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(email)) {
                    Toast.makeText(Register.this, "Please Enter Email", Toast.LENGTH_LONG).show();
                }
                else {
                    //Get snapshot of database
                    database.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //Initialize authenticator to store field verification booleans
                            Authenticator auth = new Authenticator();
                            //Check if password fits criteria
                            auth.validatePassword(password);
                            //Checks if username exits in database snapshot
                            if(!snapshot.child(username).exists()) {
                                //Sets username in authenticator to valid
                                auth.setUsernameValidTrue();
                                boolean flag = true;
                                //Iterate through all users
                                for(DataSnapshot data : snapshot.getChildren()) {
                                    //If email is found to already exit, sets flag to false, displays message
                                    if(data.child("email").getValue().equals(email)) {
                                        flag = false;
                                        Toast.makeText(Register.this, "Email Already Exists", Toast.LENGTH_LONG).show();
                                        break;
                                    }
                                }
                                //If email is not found in system, sets email in authenticator to valid
                                if(flag) {
                                    auth.setEmailValidTrue();
                                }
                                //Runs if all fields in authenticator are valid
                                if(auth.allValid()) {
                                    //Gets field from dropdown
                                    String typeString = dropDown.getSelectedItem().toString().trim();
                                    UserType type = UserType.PARTICIPANT;
                                    //Converts string from dropdown to UserType enum
                                    if(typeString.equals("Club")) {type = UserType.CLUB;}
                                    else if(typeString.equals("Admin")) {type = UserType.ADMIN;}
                                    //Creates instance of user and adds to database
                                    //Crashing here on create club
                                    User user = Register.this.addUser(type,username,email,password);
                                    user.setRoleString(type.toString());
                                    //Sets logged in user to user added to database (CurrentUserData.user)
                                    CurrentUserData.setCurrentUser(user);
                                    MyApplication.setCurUser(user);
                                    //Starts welcome screen activity
                                    Intent intent = new Intent(getApplicationContext(), Participant.class);
                                    //If admin, changes intent to Main activity
                                    if(type == UserType.ADMIN) {
                                        intent = new Intent(getApplicationContext(), MainActivity.class);
                                    }else if(type == UserType.CLUB){
                                        intent = new Intent(getApplicationContext(), Club.class);
                                    }
                                    startActivity(intent);
                                    finish();
                                }
                                //If password field is not valid, displays message
                                if(!auth.getPasswordValid()) {
                                    Toast.makeText(Register.this, "Invalid Password, Please include a special character", Toast.LENGTH_LONG).show();
                                }
                            }
                            //If username field is not valid, displays message
                            else {
                                Toast.makeText(Register.this, "Username Already Exists", Toast.LENGTH_LONG).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });
                }
            }
        });
    }

    /**
     * Adds user to database under Users path
     *
     * @param type User role of user being added
     * @param username Username of User being added, used as child key of Users
     * @param email Email of user being added
     * @param password Password of user being added
     * @return user instance with data added to database
     */
    private User addUser(UserType type, String username, String email, String password) {
        //Creates user to be returned
        User user = new User(type, username, email, password);
        //Adds user data as child <Username> under Users path
        database.child(username).setValue(user);

        database.child(username).child("type").setValue(type);
        database.child(username).child("email").setValue(email);
        database.child(username).child("password").setValue(password);
        //Clears text fields
        editTextUsername.setText("");
        editTextPassword.setText("");
        editTextEmail.setText("");
        //Sends message that user has been added
        Toast.makeText(Register.this, "User Added", Toast.LENGTH_SHORT).show();
        return user;
    }

    public static String usernameNotAdmin(String username){
        String valid = null;

        if(username == "admin"){
            valid = "false";
        }else {
            valid = "true";
        }

        return valid;
    }
}
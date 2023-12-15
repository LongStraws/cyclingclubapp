package com.example.cyclingclubapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class WelcomeScreen extends AppCompatActivity {

    Button button;
    TextView textView1;
    TextView textView2;
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        button = findViewById(R.id.logout);
        textView1 = findViewById(R.id.user_details);
        textView2 = findViewById(R.id.role);
        user = CurrentUserData.getCurrentUser();

        if(!CurrentUserData.isLoggedIn()) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }
        else {
            String role = "Participant";
            if(user.getRole() == UserType.CLUB) {
                role = "Club member";
            }
            textView1.setText(user.getUsername());
            textView2.setText(role);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CurrentUserData.logoutCurrentUser();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        //Sets a 1 second timer to update the user role, upon registration updating profile takes longer than login


    }

}
package com.example.cyclingclubapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {
    private TextView name;
    private TextView email;
    private ImageView backBtn;
    private TextView type;
    private ImageView icon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        backBtn = findViewById(R.id.back);
        icon = findViewById(R.id.profile_icon);
        name = findViewById(R.id.user_name);
        email = findViewById(R.id.user_email);
        type = findViewById(R.id.sole_type);

        User curUser = MyApplication.getCurUser();
        type.setText("Role:  " + curUser.roleString);
        email.setText("Email:  " + curUser.email);
        name.setText("UserName:  " + curUser.username);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (curUser.roleString.equals("CLUB")) {
            icon.setVisibility(View.VISIBLE);
        } else {
            icon.setVisibility(View.INVISIBLE);
        }
    }
}

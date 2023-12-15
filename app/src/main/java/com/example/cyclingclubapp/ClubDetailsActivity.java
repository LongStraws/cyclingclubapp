package com.example.cyclingclubapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ClubDetailsActivity extends AppCompatActivity {

    private EditText gameDescription;
    private EditText gameDistance;
    private EditText gameHeight;
    private EditText gameLandMark;
    private EditText gameLevel;
    private EditText gameRouterDes;
    private EditText gameCost;
    private EditText gameParticipantNum;
    private String eventId;
    private Button addEventBtn;
    private Button logoutBtn;
    private GameDetails gameDetails;
    private String roleString;
    private Button deleteEvent;
    private ImageView backImage;
    private ImageView setting;
    private Button updateEvent;

    private String gameDescriptionValue;
    private String gameDistanceValue;
    private String gameHeightValue;
    private String gameLandMarkValue;
    private String gameLevelValue;
    private String gameRouterDesValue;
    private String gameCostValue;
    private String gameParticipantNumValue;
    private String eventName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.club_edit_activity);
        gameDescription = findViewById(R.id.editTextDescription);
        gameDistance = findViewById(R.id.editTextAge);
        gameHeight = findViewById(R.id.editTextDistance);
        updateEvent = findViewById(R.id.updateEvent);
        setting = findViewById(R.id.setting);
        gameLandMark = findViewById(R.id.editTextLevel);
        gameLevel = findViewById(R.id.editTextSafety);
        gameRouterDes = findViewById(R.id.editTextConditions);
        gameCost = findViewById(R.id.editTextElevation);
        gameParticipantNum = findViewById(R.id.editTextLength);
        addEventBtn = findViewById(R.id.AddEvent);
        addEventBtn.setVisibility(View.GONE);
        deleteEvent = findViewById(R.id.deleteEvent);
        logoutBtn = findViewById(R.id.logout);
        backImage = findViewById(R.id.back);

        roleString = MyApplication.getCurUser().roleString;

        Intent intent = getIntent();
        eventId = intent.getStringExtra("eventId");
        eventName = intent.getStringExtra("eventName");
        gameDetails = (GameDetails) intent.getSerializableExtra("gameDetails");


        addEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (roleString.equals("CLUB")) {
                    Intent intent1 = new Intent();
                    intent1.putExtra("eventId", eventId);
                    intent1.putExtra("eventName", eventName);
                    intent1.setClass(ClubDetailsActivity.this, AddClubActivity.class);
                    startActivity(intent1);
                    finish();
                }

            }
        });

        if (roleString.equals("CLUB")) {
           deleteEvent.setVisibility(View.VISIBLE);
        } else if (roleString.equals("PARTICIPANT")) {
            deleteEvent.setVisibility(View.GONE);
        }

        deleteEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();

                String gameId = gameDetails.gameId;
                DatabaseReference nodeRef = databaseRef.child("Games").child(eventId).child(gameId);
                nodeRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("FirebaseData", "Node deleted successfully");
                            finish();
                        } else {
                            Log.w("FirebaseData", "Failed to delete node", task.getException());
                        }
                    }
                });

            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().getInstance().signOut();
                CurrentUserData.logoutCurrentUser();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ClubDetailsActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
        });

        updateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isRightData = isRightGame();
                if (isRightData) {
                    DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference gameRef = databaseRef.child("Games").child(eventId).child(gameDetails.gameId);
                    gameDetails.gameDescribe = gameDescriptionValue;
                    gameDetails.gameDistance = gameDistanceValue;
                    gameDetails.gameHeight = gameHeightValue;
                    gameDetails.gameLandMark = gameLandMarkValue;
                    gameDetails.gameLevel = gameLevelValue;
                    gameDetails.gameRouterDes = gameRouterDesValue;
                    gameDetails.gameCost = gameCostValue;
                    gameDetails.gameParticipantNum = gameParticipantNumValue;

                    gameRef.setValue(gameDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                                Log.d("UpdateData", "Game object updated successfully");
                                Toast.makeText(ClubDetailsActivity.this,"Game data updated successfully!",Toast.LENGTH_SHORT).show();
                            } else {

                                Toast.makeText(ClubDetailsActivity.this,"Game data updated failed!",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        refreshGameDetails();

        refreshClub();

    }

    private boolean isRightGame() {
        gameDescriptionValue = gameDescription.getText().toString();
        gameDistanceValue = gameDistance.getText().toString();
        gameHeightValue = gameHeight.getText().toString();
        gameLandMarkValue = gameLandMark.getText().toString();
        gameLevelValue = gameLevel.getText().toString();
        gameRouterDesValue = gameRouterDes.getText().toString();
        gameCostValue = gameCost.getText().toString();
        gameParticipantNumValue = gameParticipantNum.getText().toString();
        if (TextUtils.isEmpty(gameDescriptionValue) ||TextUtils.isEmpty(gameDistanceValue) ||
                TextUtils.isEmpty(gameHeightValue) || TextUtils.isEmpty(gameLandMarkValue) ||
                TextUtils.isEmpty(gameLevelValue) || TextUtils.isEmpty(gameRouterDesValue) ||
                TextUtils.isEmpty(gameCostValue) || TextUtils.isEmpty(gameParticipantNumValue)) {
            Toast.makeText(ClubDetailsActivity.this,"please enter all data!",Toast.LENGTH_SHORT).show();
            return false;
        }
        try {
            Double.parseDouble(gameDistanceValue);
        }
        catch (NumberFormatException e) {
            Toast.makeText(ClubDetailsActivity.this,"please enter number for distance!",Toast.LENGTH_SHORT).show();
            return false;
        }
        try {
            Double.parseDouble(gameHeightValue);
        }
        catch (NumberFormatException e) {
            Toast.makeText(ClubDetailsActivity.this,"please enter number for height!",Toast.LENGTH_SHORT).show();
            return false;
        }
        try {
            Integer.parseInt(gameLevelValue);
        }
        catch (NumberFormatException e) {
            Toast.makeText(ClubDetailsActivity.this,"please enter number for level!",Toast.LENGTH_SHORT).show();
            return false;
        }
        try {
            Double.parseDouble(gameCostValue);
        }
        catch (NumberFormatException e) {
            Toast.makeText(ClubDetailsActivity.this,"please enter number for cost!",Toast.LENGTH_SHORT).show();
            return false;
        }
        try {
            Integer.parseInt(gameParticipantNumValue);
        }
        catch (NumberFormatException e) {
            Toast.makeText(ClubDetailsActivity.this,"please enter number for number of participants!",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }



    private void refreshClub() {
        if (roleString.equals("CLUB")) {
            addEventBtn.setText("Add Game");
        }
    }


    private void refreshGameDetails() {
        gameDescription.setText(gameDetails.getGameDescribe());
        gameDistance.setText(gameDetails.getGameDistance());
        gameHeight.setText( gameDetails.getGameHeight());
        gameLandMark.setText( gameDetails.getGameLandMark());
        gameLevel.setText( gameDetails.getGameLevel());
        gameRouterDes.setText( gameDetails.getGameRouterDes());
        gameCost.setText( gameDetails.getGameCost());
        gameParticipantNum.setText( gameDetails.getGameParticipantNum());
    }

    public static boolean isRightGameUnitTest(String gameDescriptionValue, String gameDistanceValue, String gameHeightValue, String gameLandMarkValue, String gameLevelValue, String gameRouterDesValue, String gameCostValue, String gameParticipantNumValue) {

        try {
            Double.parseDouble(gameDistanceValue);
        }
        catch (NumberFormatException e) {
            return false;
        }
        try {
            Double.parseDouble(gameHeightValue);
        }
        catch (NumberFormatException e) {
            return false;
        }
        try {
            Integer.parseInt(gameLevelValue);
        }
        catch (NumberFormatException e) {
            return false;
        }
        try {
            Double.parseDouble(gameCostValue);
        }
        catch (NumberFormatException e) {
            return false;
        }
        try {
            Integer.parseInt(gameParticipantNumValue);
        }
        catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}

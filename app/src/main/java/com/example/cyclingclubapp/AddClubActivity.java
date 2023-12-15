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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddClubActivity extends AppCompatActivity {

    private EditText gameDescription;
    private EditText gameDistance;
    private EditText gameHeight;
    private EditText gameLandMark;
    private EditText gameLevel;
    private EditText gameRouterDes;
    private EditText gameCost;
    private EditText gameParticipantNum;
    private DatabaseReference databaseRef;
    private String eventId;
    private String userName;
    private Button addEventBtn;
    private Button logoutBtn;
    private String gameDescriptionValue;
    private String gameDistanceValue;
    private String gameHeightValue;
    private String gameLandMarkValue;
    private String gameLevelValue;
    private String gameRouterDesValue;
    private String gameCostValue;
    private String gameParticipantNumValue;
    private ImageView backImage;
    private ImageView setting;
    private String eventNames;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.club_add_activity);
        gameDescription = findViewById(R.id.editTextDescription);
        gameDistance = findViewById(R.id.editTextAge);
        gameHeight = findViewById(R.id.editTextDistance);
        setting = findViewById(R.id.setting);
        gameLandMark = findViewById(R.id.editTextLevel);
        gameLevel = findViewById(R.id.editTextSafety);
        gameRouterDes = findViewById(R.id.editTextConditions);
        gameCost = findViewById(R.id.editTextElevation);
        gameParticipantNum = findViewById(R.id.editTextLength);
        addEventBtn = findViewById(R.id.AddEvent);
        logoutBtn = findViewById(R.id.logout);
        backImage = findViewById(R.id.back);

        Intent intent = getIntent();
        eventId = intent.getStringExtra("eventId");
        eventNames = intent.getStringExtra("eventName");

        // FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        User user = MyApplication.getCurUser();
        if (user != null) {
            //if user has login in
            userName = user.getUsername();
        } else {

        }

        addEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isRightData = refreshGameDetails();
                // uploadGameToFireBase();

                if (isRightData) {
                    uploadGameToFireBase();
                }
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
                intent.setClass(AddClubActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
        });

    }

    private void uploadGameToFireBase() {

        databaseRef = FirebaseDatabase.getInstance().getReference("Games");

        // create GameDetails object,
        List<User> registerUser = new ArrayList<>();
        GameDetails gameDetails = new GameDetails(
                userName,
                "gameId1",
                "a tt game on sunday",
                "5km",
                "100m",
                "New York city",
                "beginner",
                "easy",
                "100$",
                "50",
                "Time Trials",
                eventId,
                registerUser


        );

        if (true) {
            gameDetails.curUserName = userName;
            gameDetails.gameDescribe = gameDescriptionValue;
            gameDetails.gameDistance = gameDistanceValue;
            gameDetails.gameHeight = gameHeightValue;
            gameDetails.gameLandMark = gameLandMarkValue;
            gameDetails.gameLevel = gameLevelValue;
            gameDetails.gameRouterDes = gameRouterDesValue;
            gameDetails.gameCost = gameCostValue;
            gameDetails.gameParticipantNum = gameParticipantNumValue;
            gameDetails.eventName = eventNames;
        }

        DatabaseReference gameRef = databaseRef.child(eventId);
        gameRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    GameDetails detail = childSnapshot.getValue(GameDetails.class);
                    if (detail == null) {
                        continue;
                    }
                    if (detail.equals(gameDetails)) {
                        Toast.makeText(AddClubActivity.this,"you had create the game!",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                reallyUpload(gameDetails);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.e("FirebaseData", "loadEvent:onCancelled", databaseError.toException());
                reallyUpload(gameDetails);
            }
        });
    }

    private void reallyUpload(GameDetails gameDetails){

        String gamekey = databaseRef.child(eventId).push().getKey(); //
        gameDetails.setGameId(gamekey);
        databaseRef.child(eventId).child(gamekey).setValue(gameDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                    Toast.makeText(AddClubActivity.this, "Game details saved successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {

                    Toast.makeText(AddClubActivity.this, "Failed to save game details", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private boolean refreshGameDetails() {
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
            Toast.makeText(AddClubActivity.this,"please enter all data!",Toast.LENGTH_SHORT).show();
            return false;
        }
        try {
            Double.parseDouble(gameDistanceValue);
        }
        catch (NumberFormatException e) {
            Toast.makeText(AddClubActivity.this,"please enter number for distance!",Toast.LENGTH_SHORT).show();
            return false;
        }
        try {
            Double.parseDouble(gameHeightValue);
        }
        catch (NumberFormatException e) {
            Toast.makeText(AddClubActivity.this,"please enter number for height!",Toast.LENGTH_SHORT).show();
            return false;
        }
        try {
            Integer.parseInt(gameLevelValue);
        }
        catch (NumberFormatException e) {
            Toast.makeText(AddClubActivity.this,"please enter number for level!",Toast.LENGTH_SHORT).show();
            return false;
        }
        try {
            Double.parseDouble(gameCostValue);
        }
        catch (NumberFormatException e) {
            Toast.makeText(AddClubActivity.this,"please enter number for cost!",Toast.LENGTH_SHORT).show();
            return false;
        }
        try {
            Integer.parseInt(gameParticipantNumValue);
        }
        catch (NumberFormatException e) {
            Toast.makeText(AddClubActivity.this,"please enter number for number of participants!",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public static boolean refreshGameDetailsUnitTest(String gameDescriptionValue, String gameDistanceValue, String gameHeightValue, String gameLandMarkValue, String gameLevelValue, String gameRouterDesValue, String gameCostValue, String gameParticipantNumValue) {


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

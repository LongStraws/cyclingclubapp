package com.example.project31;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class ParticipantRegisterActivity extends AddClubActivity {
    private TextView gameDescription;

    private TextView gameDistance;

    private TextView gameHeight;

    private TextView gameLandMark;

    private TextView gameLevel;

    private TextView gameRouterDes;

    private TextView gameCost;

    private TextView gameParticipantNum;

    private Button registerBtn;

    private Button logoutBtn;

    private GameDetails gameDetails;

    private ImageView backImage;

    private TextView numRemaining;

    private int remainNum;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.participant_register_activity);
        gameDescription = findViewById(R.id.editTextDescription);
        gameDistance = findViewById(R.id.editTextDistance);
        gameHeight = findViewById(R.id.editTextHeight);
        numRemaining = findViewById(R.id.num_remaining);
        gameLandMark = findViewById(R.id.editTextLandMark);
        gameLevel = findViewById(R.id.editTextSafety);
        gameRouterDes = findViewById(R.id.editTextConditions);
        gameCost = findViewById(R.id.editTextPrice);
        gameParticipantNum = findViewById(R.id.editTextNum);
        registerBtn = findViewById(R.id.register_game);
        logoutBtn = findViewById(R.id.logout);
        backImage = findViewById(R.id.back);

        Intent intent = getIntent();
        gameDetails = (GameDetails) intent.getSerializableExtra("gameDetails");

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerBtn.setText("register this game");
                // upload the game, allow user to register
                uploadParticipant();


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

        int totalNum = 0;
        try {
            totalNum = Integer.parseInt(gameDetails.getGameParticipantNum());
        }catch (Exception e){

        }

        int curNum = gameDetails.registerUsers == null ? 0:gameDetails.registerUsers.size();
        remainNum = totalNum-curNum;
        numRemaining.setText("Number of remaining registration: " + " "+ (remainNum));


        // update the new info for participant
        refreshGameDetails();




    }

    private void uploadParticipant(){
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Games").child(gameDetails.eventId);
        User curUser = MyApplication.getCurUser();
        // decide user status
        databaseRef.child(gameDetails.gameId).addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                GameDetails data =  dataSnapshot.getValue(GameDetails.class);
                int num = 0;
                try {
                    num = Integer.parseInt(data.getGameParticipantNum());
                    if (num <=0) {
                        Toast.makeText(ParticipantRegisterActivity.this, "This event is not allowed to register!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch(Exception e){

                }
                List<User> userList1 = data.getRegisterUsers();
                if (userList1 == null || userList1.size() == 0){
                    registerParticipant(curUser, databaseRef, userList1);
                    return;

                }
                if (userList1.size() >= num){
                    Toast.makeText(ParticipantRegisterActivity.this, "The Game has reached the maximum number of registrations", Toast.LENGTH_SHORT).show();
                    return;
                }
                for (User user : userList1){
                    if (user.username.equals(curUser.username)){
                        Toast.makeText(ParticipantRegisterActivity.this, "You had register this game!", Toast.LENGTH_SHORT).show();
                        return;

                    }
                }

                registerParticipant(curUser, databaseRef, userList1);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // print wrong data message
                Log.e("FirebaseData", "Error fetching data", databaseError.toException());
                registerParticipant(curUser, databaseRef, null);

            }
        });


    }

    private void registerParticipant(User user, DatabaseReference databaseRef, List<User> userList){
        if (userList == null){
            ArrayList<User> users = new ArrayList<>();
            users.add(user);
            gameDetails.setRegisterUsers(users);
        }else{
            userList.add(user);
            gameDetails.setRegisterUsers(userList);

        }

        String gameKey = gameDetails.gameId;

        databaseRef.child(gameKey).setValue(gameDetails).addOnCompleteListener(new OnCompleteListener<Void>(){
            @Override
            public void onComplete( Task<Void> task){
                if (task.isSuccessful()){
                    Toast.makeText(ParticipantRegisterActivity.this, "Participant register successfully", Toast.LENGTH_SHORT).show();
                    remainNum--;
                    numRemaining.setText("Number of remaining registration: " + " "+ (remainNum)); // indicate how many numbers left that can be registered

                }else{
                    Toast.makeText(ParticipantRegisterActivity.this, "Participant register failed", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void refreshGameDetails(){
        gameDescription.setText("Event Description：" + gameDetails.getGameDescribe());
        gameDistance.setText("Input distance：" + gameDetails.getGameDistance());
        gameHeight.setText("Enter altitude：" + gameDetails.getGameHeight());
        gameLandMark.setText("Enter landmark：" + gameDetails.getGameLandMark());
        gameLevel.setText("Difficulty level：" + gameDetails.getGameLevel());
        gameRouterDes.setText("Route details：" + gameDetails.getGameRouterDes());
        gameCost.setText("Registration fee：" + gameDetails.getGameCost());
        gameParticipantNum.setText("Participant limit：" + gameDetails.getGameParticipantNum());

    }




}

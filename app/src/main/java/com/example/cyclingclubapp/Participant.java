package com.example.cyclingclubapp;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import java.util.List;

public class Participant  extends AppCompatActivity {
    private RecyclerView recyclerView;
    private GameAdapter gameAdapter;

    private List<GameDetails> gameDetailsList = new ArrayList<>();
    private String curUserType;
    private Button logoutBtn;

    private EditText keywords;
    private ImageView seacrhBtn;
    private TextView searchError;

    private Button viewGames;

    private TextView type;

    private TextView name;
    private TextView email;
    private User curUser;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.participant_activity);
        recyclerView = findViewById(R.id.recyclerView);
        searchError = findViewById(R.id.search_error);
        logoutBtn = findViewById(R.id.logout);
        name = findViewById(R.id.user_name);
        email = findViewById(R.id.user_email);
        type = findViewById(R.id.sole_type);
        keywords = findViewById(R.id.search_edit);
        seacrhBtn = findViewById(R.id.search_btn);
        viewGames = findViewById(R.id.view_games);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        gameAdapter = new GameAdapter();

        curUserType = MyApplication.getCurUser().roleString;

        curUser = MyApplication.getCurUser();
        type.setText("Role:  " + curUser.roleString);
        email.setText("Email:  " + curUser.email);
        name.setText("UserName:  " + curUser.username);

        gameAdapter.setOnItemClickListener(new OnItemClickListener2() {
            @Override
            public void onItemClick(GameDetails gameDetails) {
                Intent intent = new Intent();
                intent.putExtra("eventId", gameDetails.getEventId());
                intent.putExtra("eventName", gameDetails.getEventName());
                intent.putExtra("gameDetails", gameDetails);
                intent.setClass(Participant.this, ParticipantRegisterActivity.class); // active PRA class
                Participant.this.startActivity(intent);
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

        viewGames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Participant_Games_View.class);
                startActivity(intent);
                finish();
            }
        });


        seacrhBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(keywords.getText().toString())) {
                    Toast.makeText(Participant.this,"please enter keywords to search",Toast.LENGTH_SHORT).show();
                    return;
                }
                initFireBase();
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        initFireBase();
    }

    private void initFireBase() {
        String keySearch = keywords.getText().toString();
        if (TextUtils.isEmpty(keySearch)) return;
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Games");
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                gameDetailsList.clear();
                for (DataSnapshot gameSnapshot : dataSnapshot.getChildren()) {

                    for (DataSnapshot detailSnapshot : gameSnapshot.getChildren()) {
                        GameDetails details = detailSnapshot.getValue(GameDetails.class);
                        if (details.eventName.equalsIgnoreCase(keySearch) || details.curUserName.equalsIgnoreCase(keySearch)) {
                            gameDetailsList.add(details);
                        }
                    }
                }
                gameAdapter.setData(gameDetailsList);
                recyclerView.setAdapter(gameAdapter);
                if (gameDetailsList.size() ==0) {
                    searchError.setVisibility(View.VISIBLE);
                } else {
                    searchError.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("FirebaseData", "loadEvent:onCancelled", databaseError.toException());
            }
        });

    }




}

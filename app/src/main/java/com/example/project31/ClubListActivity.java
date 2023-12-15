package com.example.project31;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
import java.util.Map;


public class ClubListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private String eventId;
    private GameAdapter gameAdapter;
    private List<GameDetails> gameDetailsList = new ArrayList<>();
    private Button addGame;
    private String curUserName;
    private String curUserType;
    private Button logoutBtn;
    private ImageView backImage;
    private ImageView setting;

    private String eventName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.club_list);
        recyclerView = findViewById(R.id.recyclerView);
        addGame = findViewById(R.id.AddEvent_1);
        logoutBtn = findViewById(R.id.logout);
        backImage = findViewById(R.id.back);
        setting = findViewById(R.id.setting);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        gameAdapter = new GameAdapter();
        Intent intent = getIntent();
        eventId = intent.getStringExtra("eventId");
        eventName = intent.getStringExtra("eventName");
        curUserName = MyApplication.getCurUser().username;
        curUserType = MyApplication.getCurUser().roleString;
        initFireBase();

        gameAdapter.setOnItemClickListener(new OnItemClickListener2() {
            @Override
            public void onItemClick(GameDetails gameDetails) {
                // start new acticity
                Intent intent = new Intent();
                intent.putExtra("eventId", eventId);
                intent.putExtra("eventName", eventName);
                intent.putExtra("gameDetails", gameDetails);
                intent.setClass(ClubListActivity.this, ClubDetailsActivity.class);
                ClubListActivity.this.startActivity(intent);
                finish();
            }
        });
//



        addGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent1.putExtra("eventId",eventId);
                intent1.putExtra("eventName",eventName);
                intent1.setClass(ClubListActivity.this, AddClubActivity.class);
                startActivity(intent1);
                finish();
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
                intent.setClass(ClubListActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
        });


    }



    private void initFireBase() {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Games");
        DatabaseReference eventIdRef = databaseRef.child(eventId);

        eventIdRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    GameDetails detail = childSnapshot.getValue(GameDetails.class);
                    String curUserName = detail.curUserName;
                    if (curUserType.equals("CLUB")) {
                        if (MyApplication.getCurUser().username.equals(curUserName)) {
                            gameDetailsList.add(detail);
                        }
                    }

                }
                if (curUserType.equals("CLUB")) {
                    addGame.setVisibility(View.VISIBLE);
                }

                // Update adapter after data changes

                gameAdapter.setData(gameDetailsList);
                recyclerView.setAdapter(gameAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("FirebaseData", "loadEvent:onCancelled", databaseError.toException());
            }
        });

    }

}

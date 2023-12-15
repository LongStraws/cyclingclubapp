package com.example.project31;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;



import java.util.ArrayList;
import java.util.List;

public class Participant_Games_View extends AppCompatActivity {

    private Button back;
    private  RecyclerView games;

    private GameAdapter gameAdapter;
    private List<GameDetails> list;

    private DatabaseReference db = FirebaseDatabase.getInstance().getReference("Games");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_games_view);
        back = findViewById(R.id.back);
        games = findViewById(R.id.games);

        games.setLayoutManager(new LinearLayoutManager(this));
        gameAdapter = new GameAdapter();
        list = new ArrayList<GameDetails>();
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot d1 : snapshot.getChildren()) {
                    for(DataSnapshot d2 : d1.getChildren()) {
                        DataSnapshot d3 = d2.child("registerUsers");
                        for(DataSnapshot d4 : d3.getChildren()) {
                            String user = d4.child("username").getValue(String.class);
                            if(user.equals(MyApplication.getCurUser().getUsername())) {
                                GameDetails tempDetails = d2.getValue(GameDetails.class);
                                list.add(tempDetails);
                            }
                        }
                    }
                }
                gameAdapter.setData(list);
                games.setAdapter(gameAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Participant.class);
                startActivity(intent);
                finish();
            }
        });


    }


}
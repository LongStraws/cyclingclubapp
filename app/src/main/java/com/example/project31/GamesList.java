package com.example.project31;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import android.view.View;
import android.widget.ListView;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class GamesList extends AppCompatActivity {

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Games");
    Button back;
    ListView list;

    List<GameDetails> games;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_list);
        back = (Button) findViewById(R.id.back1);
        list = (ListView) findViewById(R.id.gamesList);
        games = new ArrayList<GameDetails>();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Club.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                games.clear();
                for(DataSnapshot first : snapshot.getChildren()) {
                    for(DataSnapshot second : first.getChildren()) {
                        GameDetails gameDetails = second.getValue(GameDetails.class);
                        games.add(gameDetails);
                    }
                }
                GamesListView adapter = new GamesListView(GamesList.this, games);
                list.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
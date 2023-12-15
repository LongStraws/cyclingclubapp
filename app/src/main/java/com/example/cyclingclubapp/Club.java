package com.example.cyclingclubapp;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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


public class Club extends AppCompatActivity {

    List<Event> groupedEvents = new ArrayList<>();

    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;
    private Button logoutBtn;

    private Button gamesListBtn;
    private ImageView backImage;
    private ImageView setting;


    private TextView type;
    private ImageView icon;

    private TextView name;
    private TextView email;
    private boolean isExist;
    private boolean isExist2;
    private TextView connectName;
    private TextView phone;
    private TextView sharedLink;
    private User curUser;
    private ClubUser mClubUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.club_activity);
        recyclerView = findViewById(R.id.recyclerView);
        logoutBtn = findViewById(R.id.logout_btn);
        gamesListBtn = findViewById(R.id.gameslist_btn);
        setting = findViewById(R.id.setting);
        icon = findViewById(R.id.profile_icon);
        name = findViewById(R.id.user_name);
        email = findViewById(R.id.user_email);
        type = findViewById(R.id.sole_type);
        connectName = findViewById(R.id.con_name);
        phone = findViewById(R.id.phone);
        sharedLink = findViewById(R.id.shared);

        backImage = findViewById(R.id.back);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        eventAdapter = new EventAdapter();
        initFireBase();
        eventAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Event event) {
                // Handle click events, such as starting a new Activity or displaying a dialog box
                Intent intent = new Intent();
                intent.putExtra("eventId", event.getId());
                intent.putExtra("eventName", event.getEventName());
                intent.setClass(Club.this, ClubListActivity.class);
                Club.this.startActivity(intent);
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

        gamesListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getApplicationContext(), GamesList.class);
                startActivity(intent2);
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
                intent.setClass(Club.this,ProfileActivity.class);
                startActivity(intent);
            }
        });
        // initSearch();

        curUser = MyApplication.getCurUser();
        type.setText("Role:  " + curUser.roleString);
        email.setText("Email:  " + curUser.email);
        name.setText("UserName:  " + curUser.username);


        if (curUser.roleString.equals("CLUB")) {
            icon.setVisibility(View.VISIBLE);
            type.setVisibility(View.VISIBLE);
            email.setVisibility(View.VISIBLE);
            name.setVisibility(View.VISIBLE);
            connectName.setVisibility(View.VISIBLE);
            phone.setVisibility(View.VISIBLE);
            sharedLink.setVisibility(View.VISIBLE);
        } else {
            icon.setVisibility(View.GONE);
            type.setVisibility(View.GONE);
            email.setVisibility(View.GONE);
            name.setVisibility(View.GONE);
            connectName.setVisibility(View.GONE);
            phone.setVisibility(View.GONE);
            sharedLink.setVisibility(View.GONE);
        }
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Club");
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot clubSnapshot : snapshot.getChildren()) {
                    ClubUser clubUser = clubSnapshot.getValue(ClubUser.class);
                    if (clubUser.userName.equals(curUser.username)) {
                        isExist = true;
                    }
                }
                if (!isExist) {
                    new AlertDialog.Builder(Club.this)
                            .setTitle("prompt")
                            .setMessage("Do you need additional club user information?") //  content of information
                            // add confrim button
                            .setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent();
                                    intent.setClass(Club.this,EditClubActivity.class);
                                    startActivity(intent);
                                }
                            })
                            // add 'cancel' button
                            .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();


        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Club");
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot clubSnapshot : snapshot.getChildren()) {
                    ClubUser clubUser = clubSnapshot.getValue(ClubUser.class);
                    if (clubUser.userName.equals(curUser.username)) {
                        isExist2 = true;
                        mClubUser = clubUser;
                    }
                }
                if (isExist2) {
                    connectName.setVisibility(View.VISIBLE);
                    phone.setVisibility(View.VISIBLE);
                    sharedLink.setVisibility(View.VISIBLE);
                    connectName.setText("Connect User: " + mClubUser.connectName);
                    phone.setText("Phone Number: " + mClubUser.phone);
                    sharedLink.setText("Shared Link Url: " + mClubUser.insLink);
                } else {
                    connectName.setVisibility(View.GONE);
                    phone.setVisibility(View.GONE);
                    sharedLink.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void initFireBase() {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("events");

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {
                    // Assume have an Event class that matches the database
                    Event event = eventSnapshot.getValue(Event.class);
                    // Now can use the event object,
                    System.out.println("admin event："+event.toString());
                    groupedEvents.add(event);


                }

                // Update adapter after data changes
                eventAdapter.setData(groupedEvents);
                recyclerView.setAdapter(eventAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //  Failed to obtain data, print error log
                Log.e("FirebaseData", "loadEvent:onCancelled", databaseError.toException());
            }
        });

    }

}

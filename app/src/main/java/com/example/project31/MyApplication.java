package com.example.project31;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {
    private static User curUser;
    private Context mContext;
    private List<Event> eventList = new ArrayList<>();

    public static void setCurUser(User user) {
        curUser = user;
    }

    public static User getCurUser(){
        return curUser;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        initEvent();
    }

    private void initEvent() {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("events");

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {
                    Event event = eventSnapshot.getValue(Event.class);
                    if (event != null) {
                        eventList.add(event);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}

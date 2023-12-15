package com.example.project31;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserManagement extends AppCompatActivity {
    DatabaseReference databaseEvents;
    Button buttonLogout;
    Button buttonBackToMain;
    ListView listViewUsers;
    List<User> users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_management);

        buttonLogout = findViewById(R.id.logout);
        buttonBackToMain = findViewById(R.id.backToMain);
        listViewUsers = (ListView) findViewById(R.id.listViewUsers);
        users = new ArrayList<>();
        User user = CurrentUserData.getCurrentUser();

        databaseEvents = FirebaseDatabase.getInstance().getReference("Users");

        if(!CurrentUserData.isLoggedIn()) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }


        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().getInstance().signOut();
                CurrentUserData.logoutCurrentUser();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        buttonBackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        listViewUsers.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                User user = users.get(i);
                showDeleteDialog(user.getUsername(), user.getRoleString());
                return true;
            }
        });
        
        

    }
    @Override
    protected void onStart() {
        super.onStart();
        //attaching value event listener
        databaseEvents.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //clearing the previous artist list
                users.clear();
                //iterating through all the nodes
                for(DataSnapshot data : snapshot.getChildren()){
                    //getting event
                    String username = data.getKey();
                    String role = data.child("type").getValue().toString();
                    User test = new User(role,username);
                    User user = data.getValue(User.class);
                    //adding event to the list
                    users.add(test);
                }

                //creating adapter
                UserList usersAdapter = new UserList(UserManagement.this, users);
                //attaching adapter to the listview
                listViewUsers.setAdapter(usersAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void showDeleteDialog(final String username, String userRole) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.delete_dialog, null);
        dialogBuilder.setView(dialogView);

        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteUser);

        dialogBuilder.setTitle("Username: " + username + " Role: " + userRole);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                deleteUser(username);
                b.dismiss();
            }

        });

    }

    private boolean deleteUser(String username){
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Users").child(username);
        //removing event
        dR.removeValue();
        Toast.makeText(getApplicationContext(), "User successfully  deleted", Toast.LENGTH_LONG).show();
        return true;

    }
}
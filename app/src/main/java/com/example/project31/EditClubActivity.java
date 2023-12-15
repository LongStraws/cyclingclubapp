package com.example.project31;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditClubActivity extends AppCompatActivity {
    private EditText insLink;
    private EditText connectName;
    private EditText phone;
    private Button saveBtn;
    private Button logoutBtn;
    private User curUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_club_user_activity);
        insLink = findViewById(R.id.shared_link);
        connectName = findViewById(R.id.Connect);
        phone = findViewById(R.id.phone_number);
        saveBtn = findViewById(R.id.deleteEvent);
        logoutBtn = findViewById(R.id.logout);
        curUser = MyApplication.getCurUser();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String share = insLink.getText().toString();
                String connName = connectName.getText().toString();
                String phoneNum = phone.getText().toString();
                if (TextUtils.isEmpty(share) || TextUtils.isEmpty(connName) || TextUtils.isEmpty(phoneNum))
                {
                    Toast.makeText(EditClubActivity.this,"please complete the information",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(share.length() < 14 || !share.substring(0,14).equals("instagram.com/")) {
                    Toast.makeText(EditClubActivity.this,"please input valid instagram link",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(phoneNum.length() < 12) {
                    Toast.makeText(EditClubActivity.this,"please input valid phone number ",Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    Integer.parseInt(phoneNum.substring(0,3));
                    Integer.parseInt(phoneNum.substring(4,7));
                    Integer.parseInt(phoneNum.substring(8,12));
                }
                //613-438-8648
                catch(NumberFormatException e) {
                    Toast.makeText(EditClubActivity.this,"please input valid phone number 1",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(phoneNum.charAt(3) != '-' || phoneNum.charAt(7) != '-') {
                    Toast.makeText(EditClubActivity.this,"please input valid phone number 2",Toast.LENGTH_SHORT).show();
                    return;
                }
                ClubUser clubUser = new ClubUser(curUser.username,curUser.email,connName,share,phoneNum,"");
                DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Club");
                String id = databaseRef.push().getKey();
                clubUser.userId = id;
                databaseRef.child(id).setValue(clubUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(EditClubActivity.this, "Club user information saved successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {

                            Toast.makeText(EditClubActivity.this, "Club user information saved failed", Toast.LENGTH_SHORT).show();
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
    }
}

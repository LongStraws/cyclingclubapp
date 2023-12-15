package com.example.project31;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity {

    DatabaseReference databaseEvents;
    Button buttonLogout;
    Button buttonManageUsers;
    RadioGroup eventType;
    RadioButton buttonTimeTrials;
    RadioButton buttonHillClimb;
    RadioButton buttonRoadStageRace;
    RadioButton eventChosen;
    TextView textView1;
    TextView textView2;

    EditText editEventDesc;
    EditText editEventAge;
    EditText editEventDistance;
    EditText editEventLevel;
    EditText editEventSafety;
    EditText editEventConditions;
    EditText editEventElevation;
    EditText editEventLength;
    EditText editEventStage;
    EditText editEventRules;
    ScrollView scrollView;
    ListView listViewEvents;
    Button buttonAddEvent;

    List<Event> events;



    @SuppressLint({"MissingInflatedId", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listViewEvents = (ListView) findViewById(R.id.listViewEvents);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        buttonLogout = findViewById(R.id.logout);
        buttonAddEvent = findViewById(R.id.AddEvent);
        eventType = (RadioGroup) findViewById(R.id.radioEventType);

        buttonTimeTrials = (RadioButton) findViewById(R.id.radio_timetrials);
        buttonHillClimb = (RadioButton) findViewById(R.id.radio_hillclimb);
        buttonRoadStageRace = (RadioButton) findViewById(R.id.radio_roadstagerace);

        buttonManageUsers = (Button) findViewById(R.id.manageUserActivity);

        eventChosen = (RadioButton) findViewById(eventType.getCheckedRadioButtonId());
        eventType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                eventChosen = (RadioButton) findViewById(checkedId);

                //hides all fields
                findViewById(R.id.editTextDescription).setVisibility(View.GONE);
                findViewById(R.id.editTextAge).setVisibility(View.GONE);
                findViewById(R.id.editTextDistance).setVisibility(View.GONE);
                findViewById(R.id.editTextLevel).setVisibility(View.GONE);
                findViewById(R.id.editTextSafety).setVisibility(View.GONE);
                findViewById(R.id.editTextConditions).setVisibility(View.GONE);
                findViewById(R.id.editTextElevation).setVisibility(View.GONE);
                findViewById(R.id.editTextLength).setVisibility(View.GONE);
                findViewById(R.id.editTextStage).setVisibility(View.GONE);
                findViewById(R.id.editTextRules).setVisibility(View.GONE);

                int TT = R.id.radio_timetrials;
                int HC = R.id.radio_hillclimb;
                int RSR = R.id.radio_roadstagerace;

                //show certain fields based on button clicked
                if (checkedId == TT) {
                    findViewById(R.id.editTextDescription).setVisibility(View.VISIBLE);
                    findViewById(R.id.editTextAge).setVisibility(View.VISIBLE);
                    findViewById(R.id.editTextDistance).setVisibility(View.VISIBLE);
                    findViewById(R.id.editTextSafety).setVisibility(View.VISIBLE);
                    findViewById(R.id.editTextConditions).setVisibility(View.VISIBLE);
                    findViewById(R.id.editTextRules).setVisibility(View.VISIBLE);
                } else if (checkedId == HC) {
                    findViewById(R.id.editTextDescription).setVisibility(View.VISIBLE);
                    findViewById(R.id.editTextLevel).setVisibility(View.VISIBLE);
                    findViewById(R.id.editTextAge).setVisibility(View.VISIBLE);
                    findViewById(R.id.editTextElevation).setVisibility(View.VISIBLE);
                    findViewById(R.id.editTextLength).setVisibility(View.VISIBLE);
                    findViewById(R.id.editTextSafety).setVisibility(View.VISIBLE);
                } else if (checkedId == RSR) {
                    findViewById(R.id.editTextDescription).setVisibility(View.VISIBLE);
                    findViewById(R.id.editTextLevel).setVisibility(View.VISIBLE);
                    findViewById(R.id.editTextAge).setVisibility(View.VISIBLE);
                    findViewById(R.id.editTextSafety).setVisibility(View.VISIBLE);
                    findViewById(R.id.editTextStage).setVisibility(View.VISIBLE);
                    findViewById(R.id.editTextRules).setVisibility(View.VISIBLE);
                }
            }
        }
        );

        editEventAge = (EditText) findViewById(R.id.editTextAge);
        editEventDesc = (EditText) findViewById(R.id.editTextDescription);
        editEventDistance = (EditText) findViewById(R.id.editTextDistance);
        editEventLevel = (EditText) findViewById(R.id.editTextLevel);
        editEventSafety = (EditText) findViewById(R.id.editTextSafety);
        editEventConditions = (EditText) findViewById(R.id.editTextConditions);
        editEventRules = (EditText) findViewById(R.id.editTextRules);
        editEventElevation = (EditText) findViewById(R.id.editTextElevation);
        editEventLength = (EditText) findViewById(R.id.editTextLength);
        editEventStage = (EditText) findViewById(R.id.editTextStage);


        textView1 = findViewById(R.id.user_details);
        textView2 = findViewById(R.id.role);
        User user = CurrentUserData.getCurrentUser();

        events = new ArrayList<>();

        databaseEvents = FirebaseDatabase.getInstance().getReference("events");

        if (!CurrentUserData.isLoggedIn()) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        } else {
            textView1.setText(user.getUsername());
            UserType role = user.getRole();
            String roleString = "Admin";
            if (!role.equals(UserType.ADMIN)) {
                FirebaseAuth.getInstance().getInstance().signOut();
                CurrentUserData.logoutCurrentUser();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
            textView2.setText(roleString);
        }

        listViewEvents.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                scrollView.requestDisallowInterceptTouchEvent(true);
                int action = event.getActionMasked();
                switch (action) {
                    case MotionEvent.ACTION_UP:
                        scrollView.requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });

        buttonManageUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UserManagement.class);
                startActivity(intent);
                finish();
            }
        });

        buttonAddEvent.setOnClickListener(view -> addEvent());

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
      
        listViewEvents.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Event event = events.get(i);
                showUpdateDeleteDialog(event.getId(), event.getEventName());
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
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //clearing the previous artist list
                events.clear();
                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting event
                    Event event = postSnapshot.getValue(Event.class);
                    //adding event to the list
                    events.add(event);
                }

                //creating adapter
                EventList eventsAdapter = new EventList(MainActivity.this, events);
                //attaching adapter to the listview
                listViewEvents.setAdapter(eventsAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    private void showUpdateDeleteDialog(final String eventID, String eventName) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);


        final RadioGroup radioGroup = (RadioGroup) dialogView.findViewById(R.id.radioEventType);
        //final RadioButton eventChosen = (RadioButton) dialogView.findViewById(radioGroup.getCheckedRadioButtonId());;
        final EditText editTextDescription = (EditText) dialogView.findViewById(R.id.editTextDescription);
        final EditText editTextAge = (EditText) dialogView.findViewById(R.id.editTextAge);
        final EditText editTextDistance = (EditText) dialogView.findViewById(R.id.editTextDistance);
        final EditText editTextLevel = (EditText) dialogView.findViewById(R.id.editTextLevel);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateProduct);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteUser);
        dialogBuilder.setTitle(eventName);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        eventChosen = (RadioButton) dialogView.findViewById(eventType.getCheckedRadioButtonId());
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                eventChosen = (RadioButton) dialogView.findViewById(checkedId);

                //hides all fields in update dialog
                dialogView.findViewById(R.id.editTextDescription).setVisibility(View.GONE);
                dialogView.findViewById(R.id.editTextAge).setVisibility(View.GONE);
                dialogView.findViewById(R.id.editTextDistance).setVisibility(View.GONE);
                dialogView.findViewById(R.id.editTextLevel).setVisibility(View.GONE);
                dialogView.findViewById(R.id.editTextSafety).setVisibility(View.GONE);
                dialogView.findViewById(R.id.editTextConditions).setVisibility(View.GONE);
                dialogView.findViewById(R.id.editTextElevation).setVisibility(View.GONE);
                dialogView.findViewById(R.id.editTextLength).setVisibility(View.GONE);
                dialogView.findViewById(R.id.editTextStage).setVisibility(View.GONE);
                dialogView.findViewById(R.id.editTextRules).setVisibility(View.GONE);

                int TT = R.id.radio_timetrials;
                int HC = R.id.radio_hillclimb;
                int RSR = R.id.radio_roadstagerace;

                //shows certain fields in update dialog
                if (checkedId == TT) {
                    dialogView.findViewById(R.id.editTextDescription).setVisibility(View.VISIBLE);
                    dialogView.findViewById(R.id.editTextAge).setVisibility(View.VISIBLE);
                    dialogView.findViewById(R.id.editTextDistance).setVisibility(View.VISIBLE);
                    dialogView.findViewById(R.id.editTextSafety).setVisibility(View.VISIBLE);
                    dialogView.findViewById(R.id.editTextConditions).setVisibility(View.VISIBLE);
                    dialogView.findViewById(R.id.editTextRules).setVisibility(View.VISIBLE);
                }
                if (checkedId == HC) {
                    dialogView.findViewById(R.id.editTextDescription).setVisibility(View.VISIBLE);
                    dialogView.findViewById(R.id.editTextAge).setVisibility(View.VISIBLE);
                    dialogView.findViewById(R.id.editTextLevel).setVisibility(View.VISIBLE);
                    dialogView.findViewById(R.id.editTextSafety).setVisibility(View.VISIBLE);
                    dialogView.findViewById(R.id.editTextElevation).setVisibility(View.VISIBLE);
                    dialogView.findViewById(R.id.editTextLength).setVisibility(View.VISIBLE);
                }
                if (checkedId == RSR) {
                    dialogView.findViewById(R.id.editTextDescription).setVisibility(View.VISIBLE);
                    dialogView.findViewById(R.id.editTextAge).setVisibility(View.VISIBLE);
                    dialogView.findViewById(R.id.editTextLevel).setVisibility(View.VISIBLE);
                    dialogView.findViewById(R.id.editTextSafety).setVisibility(View.VISIBLE);
                    dialogView.findViewById(R.id.editTextStage).setVisibility(View.VISIBLE);
                    dialogView.findViewById(R.id.editTextRules).setVisibility(View.VISIBLE);
                }


            }
        }
        );


        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String eventType = eventChosen.getText().toString().trim();
                String eventDescription = editTextDescription.getText().toString().trim();
                String age = editTextAge.getText().toString().trim();
                String level = (editTextLevel.getText().toString().trim());
                String distance = editTextDistance.getText().toString();
                String safety = editEventSafety.getText().toString();
                String conditions = editEventConditions.getText().toString();
                String elevation = editEventElevation.getText().toString();
                String length = editEventLength.getText().toString();
                String stage = editEventStage.getText().toString();
                String rules = editEventRules.getText().toString();

                boolean isValidInput = isValidInput(eventType, eventDescription, age, distance, level, safety, conditions, elevation, length, stage, rules);

                try {
                    Integer.parseInt(age);
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Age must be an integer", Toast.LENGTH_LONG).show();
                    return;
                }
                try {
                    Double.parseDouble(distance);
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Distance must be a number", Toast.LENGTH_LONG).show();
                    return;
                }
                try {
                    Integer.parseInt(level);
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Level must be an integer", Toast.LENGTH_LONG).show();
                    return;
                }

                if (isValidInput) {

                    int eventAge = Integer.parseInt(editTextAge.getText().toString().trim());
                    int eventLevel = Integer.parseInt(editTextLevel.getText().toString().trim());
                    double eventDistance = Double.parseDouble(editTextDistance.getText().toString());
                    double eventElevation = Double.parseDouble((editEventElevation.getText().toString()));
                    double eventLength = Double.parseDouble(editEventLength.getText().toString());
                    //currently not in use
                    //String maker = CurrentUserData.getCurrentUser().getUsername();

                    updateEvent(eventID, eventType, eventDescription, eventAge, eventDistance, eventLevel, safety, conditions, eventElevation, eventLength, stage, rules);
                    b.dismiss();

                } else {
                    message("Make sure all fields are filled!");
                }
            }
        });


        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteEvent(eventID);
                b.dismiss();
            }
        });
    }

    private void message(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void updateEvent(String id,String eventType, String description, int age, double distance, int level, String safety, String conditions,
                             double elevation, double length, String stage, String rules) {

        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("events").child(id);

        Event event = createEvent(id, eventType, description, age, distance, level, safety, conditions, elevation, length, stage, rules);

        if(event == null) {
            Toast.makeText(getApplicationContext(), "no event chosen", Toast.LENGTH_LONG).show();
        }

        dR.setValue(event);

        Toast.makeText(getApplicationContext(), "Event Successfully Updated", Toast.LENGTH_LONG).show();

    }

    private boolean deleteEvent(String id) {
        //getting the specified event reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("events").child(id);
        //removing event
        dR.removeValue();
        Toast.makeText(getApplicationContext(), "Event successfully deleted", Toast.LENGTH_LONG).show();
        return true;
    }

    public static String deleteEventTest(String id) {
        //getting the specified event reference
        if(!id.equals("")) {
            return "true";
        }
        return "false";
    }

    private void addEvent() {
        //getting the values to save
        String eventType = eventChosen.getText().toString();
        String description = editEventDesc.getText().toString();
        String age = editEventAge.getText().toString();
        String distance = editEventDistance.getText().toString();
        String level = editEventLevel.getText().toString();
        String safety = editEventSafety.getText().toString();
        String conditions = editEventConditions.getText().toString();
        String elevation = editEventElevation.getText().toString();
        String length = editEventLength.getText().toString();
        String stage = editEventStage.getText().toString();
        String rules = editEventRules.getText().toString();

        boolean isValidInput = isValidInput(eventType, description, age, distance, level, safety, conditions, elevation, length, stage, rules);

        //checking if the value is provided for each event
        if (!isValidInput) {
            Toast.makeText(this, "Please make sure all fields are filled for " + eventType + "!", Toast.LENGTH_LONG).show();
            return;
        }

        try {

            int ageInt = Integer.parseInt(age);
            double distanceDouble = eventType.equals("Time Trials") ? Double.parseDouble(distance) : 0.0;
            int levelInt = (eventType.equals("Hill Climb") || eventType.equals("Road Stage Race")) ? Integer.parseInt(level) : 0;
            double elevationDouble = eventType.equals("Hill Climb") ? Double.parseDouble(elevation) : 0.0;
            double lengthDouble = eventType.equals("Hill Climb") ? Double.parseDouble(length) : 0.0;


            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our Product
            String id = databaseEvents.push().getKey();

            Event event = createEvent(id, eventType, description, ageInt, distanceDouble, levelInt, safety, conditions, elevationDouble, lengthDouble, stage, rules);

            if (event != null) {
                databaseEvents.child(id).setValue(event);
                clearEventFields();
                Toast.makeText(this, "Event added", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Error creating event", Toast.LENGTH_LONG).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_LONG).show();
        }

    }

    private boolean isValidInput(String eventType, String description, String age, String distance, String level, String safety, String conditions,
                                 String elevation, String length, String stage, String rules) {
        if (eventType.equals("Time Trials")) {
            return (!TextUtils.isEmpty(description) && !TextUtils.isEmpty(age) && !TextUtils.isEmpty(distance) && !TextUtils.isEmpty(safety)
                    && !TextUtils.isEmpty(conditions) && !TextUtils.isEmpty(rules));
        } else if (eventType.equals("Hill Climb")) {
            return (!TextUtils.isEmpty(description) && !TextUtils.isEmpty(age) && !TextUtils.isEmpty(level) && !TextUtils.isEmpty(safety)
                    && !TextUtils.isEmpty(elevation) && !TextUtils.isEmpty(length));
        } else if (eventType.equals("Road Stage Race")) {
            return (!TextUtils.isEmpty(description) && !TextUtils.isEmpty(age) && !TextUtils.isEmpty(level) && !TextUtils.isEmpty(safety)
                    && !TextUtils.isEmpty(stage) && !TextUtils.isEmpty(rules));
        }
        return false;
    }



    private Event createEvent(String id, String eventType, String description, int age, double distance, int level, String safety, String conditions,
                              double elevation, double length, String stage, String rules) {
        switch (eventType) {
            case "Time Trials":
                return new Event(id, eventType, description, age, distance, safety, conditions, rules);
            case "Hill Climb":
                return new Event(id, eventType, description, age, level, safety, length, elevation);
            case "Road Stage Race":
                return new Event(id, eventType, description, age, level, safety, stage, rules);
            default:
                return null;
        }
    }

    public static String validateUserType(String user){
        if(user.equals("ADMIN")){
            return  "true";
        }
        else {
            return "false";
        }
    }


    private void clearEventFields() {
        editEventDesc.setText("");
        editEventLevel.setText("");
        editEventAge.setText("");
        editEventDistance.setText("");
        editEventSafety.setText("");
        editEventConditions.setText("");
        editEventLength.setText("");
        editEventElevation.setText("");
        editEventStage.setText("");
        editEventRules.setText("");
    }

    public static String isValidInputTest(String eventType, String description, String age, String distance, String level, String safety, String conditions,
                                           String elevation, String length, String stage, String rules) {
        if (eventType.equals("Time Trials")) {
            return (!description.equals("") && !age.equals("") && !distance.equals("") && !safety.equals("")
                    && !conditions.equals("") && !rules.equals("")) ? "true" : "false";
        } else if (eventType.equals("Hill Climb")) {
            return (!description.equals("") && !age.equals("") && !level.equals("") && !safety.equals("")
                    && !elevation.equals("") && !length.equals("")) ? "true" : "false";
        } else if (eventType.equals("Road Stage Race")) {
            return (!description.equals("") && !age.equals("") && !level.equals("") && !safety.equals("")
                    && !stage.equals("") && !rules.equals("")) ? "true" : "false";
        }
        return "false";
    }
}


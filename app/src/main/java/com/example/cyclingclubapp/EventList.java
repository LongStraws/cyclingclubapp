package com.example.cyclingclubapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
public class EventList extends ArrayAdapter<Event>{
    private Activity context;
    List<Event> events;
    public EventList(Activity context, List<Event> events){
        super(context, R.layout.activity_main, events);
        this.context = context;
        this.events = events;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_event_list, null, true);

        TextView textViewCreator = (TextView) listViewItem.findViewById(R.id.textViewCreator) ;
        TextView textViewEvent = (TextView) listViewItem.findViewById(R.id.textViewEvent);
        TextView textViewDescription = (TextView) listViewItem.findViewById(R.id.textViewDescription);
        TextView textViewAge = (TextView) listViewItem.findViewById(R.id.textViewAge);
        TextView textViewDistance = (TextView) listViewItem.findViewById(R.id.textViewDistance);
        TextView textViewLevel = (TextView) listViewItem.findViewById(R.id.textViewLevel);

        Event event = events.get(position);
        //hard coded name
        textViewCreator.setText("admin");
        textViewEvent.setText(event.getEventName());
        textViewDescription.setText(event.getEventDescription());
        textViewAge.setText(Integer.toString(event.getEventAge()));
        textViewDistance.setText(Double.toString(event.getDistanceRequired()));
        textViewLevel.setText(Integer.toString(event.getLevelRequired()));

        return listViewItem;
    }



}

package com.example.project31;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private List<Event> groupedEvents;

    private OnItemClickListener listener;

    public EventAdapter(List<Event> groupedEvents) {
        this.groupedEvents = groupedEvents;
    }

    public EventAdapter() {

    }



    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, parent, false);
        return new EventViewHolder(v);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // String eventType = eventTypeList.get(position);
        Event event = groupedEvents.get(position);
        holder.eventTypeTextView.setText(event.getEventName());
        holder.eventDescriptionTextView.setText(event.getEventDescription());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(groupedEvents.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return groupedEvents.size();
    }

    public void setData(List<Event> groupedEvents) {
        this.groupedEvents = groupedEvents;

    }


    public static class EventViewHolder extends RecyclerView.ViewHolder {
        public TextView eventTypeTextView;
        public TextView eventDescriptionTextView;

        public EventViewHolder(View itemView) {
            super(itemView);
            eventTypeTextView = itemView.findViewById(R.id.eventTypeTextView);
            eventDescriptionTextView = itemView.findViewById(R.id.eventDescriptionTextView);
        }
    }

}

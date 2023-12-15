package com.example.cyclingclubapp;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.EventViewHolder> {

    private List<GameDetails> gameDetailsList;

    private OnItemClickListener2 listener;
    private ClickListener clickListener;

    public GameAdapter(List<GameDetails> gameDetailsList) {
        this.gameDetailsList = gameDetailsList;
    }

    public GameAdapter() {

    }


    // set adapter method same as eventAdapter
    public void setOnItemClickListener(OnItemClickListener2 listener) {
        this.listener = listener;
    }



    public void setOnClickListener(ClickListener listener) {
        this.clickListener = listener;
    }


    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_list_item, parent, false);
        return new EventViewHolder(v);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, @SuppressLint("RecyclerView") int position) {
        GameDetails gameDetails = gameDetailsList.get(position);
        holder.gameType.setText("event type: " + gameDetails.eventName);
        holder.gameClubName.setText("club name: " + gameDetails.curUserName);
        holder.gameDescription.setText("game description: " + gameDetails.gameDescribe);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(gameDetails);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        // return the number of events
        return gameDetailsList.size();
    }

    public void setData(List<GameDetails> gameDetailsList) {
        this.gameDetailsList = gameDetailsList;
    }


    public static class EventViewHolder extends RecyclerView.ViewHolder {

        public TextView gameClubName;

        public TextView gameType;
        public TextView gameDescription;

        public EventViewHolder(View itemView) {
            super(itemView);
            gameType = itemView.findViewById(R.id.game_type);
            gameClubName = itemView.findViewById(R.id.game_club_name);
            gameDescription = itemView.findViewById(R.id.game_description);

        }
    }

}


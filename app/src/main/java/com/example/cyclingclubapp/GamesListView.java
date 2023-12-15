package com.example.cyclingclubapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;


public class GamesListView  extends ArrayAdapter<GameDetails>{

    private Activity context;

    List<GameDetails> list;

    public GamesListView(Activity context, List<GameDetails> list){
        super(context, R.layout.activity_games_list, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.game_item, null, true);

        TextView gamename = (TextView) listViewItem.findViewById(R.id.gamename);
        TextView game_description = (TextView) listViewItem.findViewById(R.id.game_description);
        TextView gamedistance = (TextView) listViewItem.findViewById(R.id.gamedistance);
        TextView gameheight = (TextView) listViewItem.findViewById(R.id.gameheight);
        TextView gamelandmark = (TextView) listViewItem.findViewById(R.id.gamelandmark);
        TextView gamelevel = (TextView) listViewItem.findViewById(R.id.gamelevel);
        TextView gamecost = (TextView) listViewItem.findViewById(R.id.gamecost);
        TextView gameparticipants = (TextView) listViewItem.findViewById(R.id.gameparticipants);

        GameDetails gameDetails = list.get(position);
        game_description.setText("Description:   " + gameDetails.getGameDescribe());
        gamedistance.setText("Club:   " + gameDetails.getCurUserName());
        gameheight.setText("Height:   " + gameDetails.getGameHeight());
        gamelandmark.setText("Landmarks:   " + gameDetails.getGameLandMark());
        gamelevel.setText("Level:   " + gameDetails.getGameLevel());
        gamecost.setText("Cost:   " + gameDetails.getGameCost()+"$");
        gameparticipants.setText("Number of Participants:   " + gameDetails.getGameParticipantNum());
        gamename.setText("Event Name:   " + gameDetails.getEventName());

        return listViewItem;
    }
}

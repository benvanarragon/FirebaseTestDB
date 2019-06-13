package com.example.firebasetestdb;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ArtistList extends ArrayAdapter<Artist> {

    //we need to make a reference to the activity, because we need to bind it to some activity
    private Activity context;
    List<Artist> artists;

    //constructor
    public ArtistList(Activity context, List<Artist> artists){
        //calling the arrayadapter super class, passing current activity, name of the view we want to bind the data to, and then the data we are binding
        super(context, R.layout.layout_artist_list, artists);
        this.context = context;
        this.artists = artists;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        //gets an activity in that context, and makes a reference so we can reference objects within that activity
        //reference the layout we want to bind to
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_artist_list, null, true);

        //access the 2 textviews we need to populate
        //we need to call listviewitem first because these items are not in our current activity, they are in our layout template file
        TextView textViewArtistName = listViewItem.findViewById(R.id.textViewArtistName);
        TextView textViewArtistGenre = listViewItem.findViewById(R.id.textViewArtistsGenre);

        // bind the data
        Artist a = artists.get(position);
        textViewArtistName.setText(a.getArtistName());
        textViewArtistGenre.setText(a.getArtistGenre());

        //populates the two text view, and then sends back the item
        return listViewItem;
    }
}

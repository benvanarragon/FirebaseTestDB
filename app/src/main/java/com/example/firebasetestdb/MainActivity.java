package com.example.firebasetestdb;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText editTextname;
    Button buttonAddArtist;
    Spinner spinnerGenre;

    //create a databasereference object
    DatabaseReference databaseArtists;

    TextView textViewArtistName;
    TextView textViewArtistGenre;
    ListView listViewArtist;

    //list of artists objects
    List<Artist> artists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //you can leave this blank and not pass a parameter but it will pass the root node of the json tree, we want to pass the artist node
        //just connects onCreate, if someone opens the app again, the getting data process happens in onStart which gets called everytime an app opens
        databaseArtists = FirebaseDatabase.getInstance().getReference("artists");

        editTextname = findViewById(R.id.editText);
        buttonAddArtist = findViewById(R.id.button);
        spinnerGenre = findViewById(R.id.spinner);
        //instantiate list view
        listViewArtist = findViewById(R.id.listViewArtists);

//        listViewArtist.setOnTouchListener(new ListView.OnTouchListener(){
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                int action = event.getAction();
//                switch (action) {
//                    case MotionEvent.ACTION_DOWN:
//                        // Disallow ScrollView to intercept touch events.
//                        v.getParent().requestDisallowInterceptTouchEvent(true);
//                        break;
//
//                    case MotionEvent.ACTION_UP:
//                        // Allow ScrollView to intercept touch events.
//                        v.getParent().requestDisallowInterceptTouchEvent(false);
//                        break;
//                }
//
//                // Handle ListView touch events.
//                v.onTouchEvent(event);
//                return true;
//            }
//        });

        artists = new ArrayList<>();

        buttonAddArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call method below
                addArtist();
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        //create listener for data changes
        databaseArtists.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //clear the data

                artists.clear();

                //loop through datasnapshot
                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    //allows us to get 1 row at a time
                    Artist artist = postSnapshot.getValue(Artist.class);
                    //creates a new row in our artistList
                    artists.add(artist);
                }

                //bind to our list view
                ArtistList artistAdapter = new ArtistList(MainActivity.this, artists);
                listViewArtist.setAdapter(artistAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void addArtist(){
        String name = editTextname.getText().toString().trim();
        String genre = spinnerGenre.getSelectedItem().toString();

        if(!TextUtils.isEmpty(name)){
            //store to firebase db
            //create a unique id for our artists, we have to store the artist inside of this id that is uniquely generated
            String id = databaseArtists.push().getKey();

            Artist artist = new Artist(id,name,genre);

            //we can use setValue to store the values in the firebase db,
            //everytime this runs it will generate a unique id, and then inside of this id it will store the artist, if we don't do this it will overwrite the artist
            //if we pass the same id always, then the values will be overwrited, but on line 52 we are generating a new id always
            //everytime a new node will be created inside the artist node, and the artist will be stored inside of this node
            databaseArtists.child(id).setValue(artist);

            //show message
            Toast.makeText(this,"Artist added", Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(this,"You should enter a name", Toast.LENGTH_SHORT).show();
        }
    }
}

package com.example.firebasetestdb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    EditText editTextname;
    Button buttonAddArtist;
    Spinner spinnerGenre;

    //create a databasereference object
    DatabaseReference databaseArtists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //you can leave this blank and not pass a parameter but it will pass the root node of the json tree, we want to pass the artist node
        databaseArtists = FirebaseDatabase.getInstance().getReference("artists");

        editTextname = findViewById(R.id.editText);
        buttonAddArtist = findViewById(R.id.button);
        spinnerGenre = findViewById(R.id.spinner);

        buttonAddArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call method below
                addArtist();
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

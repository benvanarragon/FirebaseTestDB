package com.example.firebasetestdb;

public class Artist {

    String artistId, artistName, artistGenre;

    //blank constructor
    public Artist(){

    }
    //right click to genereate constructor for all values
    public Artist(String artistId, String artistName, String artistGenre) {
        this.artistId = artistId;
        this.artistName = artistName;
        this.artistGenre = artistGenre;
    }

    //right click for getters and setters

    public String getArtistId() {
        return artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getArtistGenre() {
        return artistGenre;
    }
}

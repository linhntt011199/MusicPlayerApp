package com.example.nct1;

import java.io.Serializable;

public class Audio implements Serializable {
    private long id;
    private String title;
    private String artist;

    public Audio(long songID, String songtitle, String songartist){
        id = songID;
        title = songtitle;
        artist = songartist;
    }
    public long getID(){
        return id;
    }
    public String getTitle(){
        return title;
    }
    public String getArtist(){
        return artist;
    }
}

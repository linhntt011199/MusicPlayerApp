package com.example.nct1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class SongAdapter extends BaseAdapter {
//    private mCallback
    private ArrayList<Audio> songs;
    private LayoutInflater songInf;
    public SongAdapter(Context c, ArrayList<Audio> theSongs){
        songs = theSongs;
        songInf = LayoutInflater.from(c);
    }
    @Override
    public int getCount(){
        return songs.size();
    }
    @Override
    public Object getItem(int arg0){
        return null;
    }
    @Override
    public long getItemId(int arg0){
        return 0;
    }

    public interface songClick {
        public void click ();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LinearLayout songLay = (LinearLayout)songInf.inflate(R.layout.song, parent, false);

//        LinearLayout ly_ln_item_song = songLay.findViewById(R.id.ly_ln_item_song);
        TextView songView = (TextView)songLay.findViewById(R.id.songtitle);
        TextView artistView = (TextView)songLay.findViewById(R.id.song_artist);
        Audio currSong = songs.get(position);
        songView.setText(currSong.getTitle());
        artistView.setText(currSong.getArtist());
        songLay.setTag(position);

//        ly_ln_item_song.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

        return songLay;
    }
}

package com.example.nct1;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nct1.MusicService.MusicBinder;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.MediaController.MediaPlayerControl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ThuVienNhac extends AppCompatActivity implements MediaPlayerControl {
    private ArrayList<Audio> songList;
    private MusicService musicSrv;
    private Intent playIntent;
    private boolean musicBound = false;
    private MusicController controller;
    private boolean paused = false, playbackPaused = false;
    private int open_menu_play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("ON Create", "On Create");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thu_vien_nhac);
        ListView songView = (ListView) findViewById(R.id.listsong);

        songList = new ArrayList<Audio>();
        getSongList();
        Collections.sort(songList, new Comparator<Audio>() {
            @Override
            public int compare(Audio a, Audio b) {
                return a.getTitle().compareTo(b.getTitle());
            }
        });
        SongAdapter songAdt = new SongAdapter(this, songList);

        Intent intent = getIntent();
        open_menu_play = intent.getIntExtra("open_menu_play", 0);
        songView.setAdapter(songAdt);
        setController();
    }

    private ServiceConnection musicConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicBinder binder = (MusicBinder) service;
            musicSrv = binder.getService();
            musicSrv.setList(songList);
            musicBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicBound = false;
        }
    };

    public void getSongList() {
        ContentResolver musicResolver = getContentResolver();
        Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);
        if (musicCursor != null && musicCursor.moveToFirst()) {
            int titleColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int idColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media._ID);
            int artistColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            do {
                long thisID = musicCursor.getLong(idColumn);
                String thistitle = musicCursor.getString(titleColumn);
                String thisartist = musicCursor.getString(artistColumn);
                songList.add(new Audio(thisID, thistitle, thisartist));
            } while (musicCursor.moveToNext());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (playIntent == null) {
            playIntent = new Intent(this, MusicService.class);
            bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);
            startService(playIntent);
        }
    }

    public void songPicked(View view) {
        musicSrv.setSong(Integer.parseInt(view.getTag().toString()));
        musicSrv.playSong();
        if (playbackPaused) {
            setController();
            playbackPaused = false;
        }

        if (!controller.isShowing()) {
            controller.show(0);
        }
    }

    @Override
    protected void onDestroy() {
        if (musicBound) unbindService(musicConnection);
        //stopService(playIntent);
        musicSrv = null;
        controller.hide();
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_end:
                stopService(playIntent);
                musicSrv = null;
                System.exit(0);
                break;
            case R.id.action_shuffle:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setController() {
        controller = new MusicController(this);
        controller.setPrevNextListeners(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playNext();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playPrev();
            }
        });
        controller.setMediaPlayer(this);
        controller.setAnchorView(findViewById(R.id.listsong));
        controller.setEnabled(true);

        if(open_menu_play == 1) {
//            controller.show(0);
        }
    }

    private void playNext() {
        musicSrv.playNext();
        if (playbackPaused) {
//            setController();
            playbackPaused = false;
        }
//        controller.show(0);
    }

    private void playPrev() {
        musicSrv.playPrev();
        if (playbackPaused) {
//            setController();
            playbackPaused = false;
        }
        controller.show(0);
    }

    @Override
    public void start() {
        musicSrv.go();
    }

    @Override
    public void pause() {
        playbackPaused = true;
        musicSrv.pausePlayer();
    }

    @Override
    public int getDuration() {
        if (musicSrv != null && musicBound && musicSrv.isPng()) return musicSrv.getDur();
        else return 0;
    }

    @Override
    public int getCurrentPosition() {
        if (musicSrv != null && musicBound && musicSrv.isPng()) return musicSrv.getPosn();
        else return 0;
    }

    @Override
    public void seekTo(int i) {
        musicSrv.seek(i);
    }

    @Override
    public boolean isPlaying() {
        if (musicSrv != null && musicBound) return musicSrv.isPng();
        return false;
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }

    @Override
    protected void onPause() {
        super.onPause();
        paused = true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.e("Media", "On Resume");

        if (controller != null) {
//            controller.show(0);
        }

        if (paused) {
//            setController();
            paused = false;
        }
    }

    @Override
    protected void onStop() {
//        controller.hide();
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        //moveTaskToBack(true);
        super.onBackPressed();
    }

}

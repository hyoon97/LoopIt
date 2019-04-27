package com.example.loopit;

import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.util.Log;
import android.os.Handler;

import java.util.ArrayList;
import java.io.IOException;

public class Player {
    private static final String LOG_TAG = "Recorder";

    private MediaPlayer player;
    private String fileName;
    private boolean isLoop;
    private int time;
//    private PlaybackParams params;
    private int currentPosition;
    private ArrayList<String> track;

    Player(ArrayList<String> trackList, boolean loop){
        track = trackList;
        isLoop = loop;
//        params = parameter;
    }

    public void play(){
        fileName = track.get(track.size()-1);
        player = new MediaPlayer();
        try{
            player.setDataSource(fileName);
            player.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare1() failed");
        }
        time = player.getDuration();
//        player.setPlaybackParams(params);
        player.setLooping(isLoop);
        player.seekTo(currentPosition);
        player.start();
    }

    public void playPrevious(){
        fileName = track.get(track.size()-2);
        player = new MediaPlayer();
        try{
            player.setDataSource(fileName);
            player.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare2() failed");
        }
//        player.setPlaybackParams(params);
        player.setLooping(isLoop);
        player.seekTo(currentPosition);
        player.start();
    }

    public void pause(){
        currentPosition = player.getCurrentPosition();
        player.pause();
    }

    public void stop(){
        currentPosition = 0;
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
    }

    public int getCurrentPosition(){
        return currentPosition;
    }

    public int getDuration(){
        return time;
    }
}

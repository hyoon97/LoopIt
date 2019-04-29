package com.example.loopit;

import android.media.MediaRecorder;
import android.util.Log;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Overdub {
    private MediaRecorder recorder;
    private String fileName;
    private Timer Handler;
    private int time;

    private static final String LOG_TAG = "Recorder";

    Overdub(String file, int duration){
        fileName = file;
        time = duration;
    }

    public void start(){
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(fileName);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try {
            recorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
        recorder.start();
        Handler = new Timer();
        Handler.schedule(stop, time+220);
    }

    private TimerTask stop = new TimerTask(){
        public void run(){
            if (recorder != null) {
                recorder.stop();
                recorder.release();
                recorder = null;
            }
        }
    };
}
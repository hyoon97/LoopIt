package com.example.loopit;

import android.media.MediaRecorder;
import android.util.Log;

import java.io.IOException;

public class Recorder {
    private MediaRecorder recorder;
    private String fileName;
//    private int time;

    private static final String LOG_TAG = "Recorder";

    Recorder(String file){
        fileName = file;
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
    }

    public void stop(){
        if (recorder != null){
            recorder.stop();
            recorder.release();
            recorder = null;
        }
    }
}

package com.example.loopit;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Handler;

public class Metronome{

    private int bpm;
    private SoundPool soundPool;
    private int maxStreams = 4;
    private int met_sound;
    private Handler handler;
    private Context mainConext;
    private int[] notes;
    private int note;
    private int kickSound;

    public Metronome(int initBpm, Context context){
        handler = new Handler();
        mainConext = context;
        notes = new int[24];
        note = 0;

        this.bpm = initBpm;
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        soundPool = new SoundPool.Builder()
                .setMaxStreams(maxStreams)
                .setAudioAttributes(audioAttributes)
                .build();

        kickSound = soundPool.load(mainConext, R.raw.kick,1);
        notes[0] = soundPool.load(mainConext, R.raw.c3,1);
        notes[1] = soundPool.load(mainConext, R.raw.db3,1);
        notes[2] = soundPool.load(mainConext, R.raw.d3,1);
        notes[3] = soundPool.load(mainConext, R.raw.eb3,1);
        notes[4] = soundPool.load(mainConext, R.raw.e3,1);
        notes[5] = soundPool.load(mainConext, R.raw.f3,1);
        notes[6] = soundPool.load(mainConext, R.raw.gb3,1);
        notes[7] = soundPool.load(mainConext, R.raw.g3,1);
        notes[8] = soundPool.load(mainConext, R.raw.ab3,1);
        notes[9] = soundPool.load(mainConext, R.raw.a3,1);
        notes[10] = soundPool.load(mainConext, R.raw.bb3,1);
        notes[11] = soundPool.load(mainConext, R.raw.b3,1);
        notes[12] = soundPool.load(mainConext, R.raw.c4,1);
        notes[13] = soundPool.load(mainConext, R.raw.db4,1);
        notes[14] = soundPool.load(mainConext, R.raw.d4,1);
        notes[15] = soundPool.load(mainConext, R.raw.eb4,1);
        notes[16] = soundPool.load(mainConext, R.raw.e4,1);
        notes[17] = soundPool.load(mainConext, R.raw.f4,1);
        notes[18] = soundPool.load(mainConext, R.raw.gb4,1);
        notes[19] = soundPool.load(mainConext, R.raw.g4,1);
        notes[20] = soundPool.load(mainConext, R.raw.ab4,1);
        notes[21] = soundPool.load(mainConext, R.raw.a4,1);
        notes[22] = soundPool.load(mainConext, R.raw.bb4,1);
        notes[23] = soundPool.load(mainConext, R.raw.b4,1);

        met_sound = kickSound;

    }


    public void incBpm(int inc){
        bpm += inc;
    }

    public void  decBpm(int dec){
        bpm -= dec;
    }

    public int getBpm() {
        return bpm;
    }

    public void changeNote(int note){
        this.note = note;
        met_sound = notes[note];
    }

    public void changeToNote(){
        met_sound = notes[note];
    }

    public void changeToClick(){
        met_sound = kickSound;
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            int delay = 60000/bpm;
            soundPool.play(met_sound,(float)0.3,(float)0.3,0,0,1);
            handler.postDelayed(this, delay);
        }
    };

    public void start(){
        runnable.run();
    }

    public void stop(){
        handler.removeCallbacks(runnable);
    }


}

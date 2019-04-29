package com.example.loopit;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.TextView;
import android.media.PlaybackParams;
import android.support.annotation.NonNull;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.support.v7.app.AppCompatActivity;

import org.w3c.dom.Text;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    //Audio Record Permission Variables
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {android.Manifest.permission.RECORD_AUDIO};

    public static Metronome metronome;
    public static boolean metIsPlaying = false;
    public static int initBpm = 100;
    public static int beats = 1;
    public static boolean beatsMode = false;
    public static int countdown = 0;
    public static boolean countdownMode = false;
    public static int duration = 0;
    public static boolean durationMode = false;

    //UI Variables
    CardView card1;
    CardView card2;
    CardView card3;
    CardView card4;

    ImageView cardImage1;
    ImageView cardImage2;
    ImageView cardImage3;
    ImageView cardImage4;

    int micResId;
    int recordResId;
    int pauseResId;
    int playResId;

    CardView trackControl1;
    CardView trackControl2;
    CardView trackControl3;
    CardView trackControl4;
    CardView allTracks;

    CardView clearButton;
    CardView overdubButton;
    CardView playButton;

    int trackControlNum;

    //Recorder and Player Variables
    private boolean isLoop = true;

    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;

    private Player overdubPlayer1;
    private Player overdubPlayer2;
    private Player overdubPlayer3;
    private Player overdubPlayer4;

    private Recorder recorder1;
    private Recorder recorder2;
    private Recorder recorder3;
    private Recorder recorder4;

    private Overdub overdub1;
    private Overdub overdub2;
    private Overdub overdub3;
    private Overdub overdub4;

    private PlaybackParams params1;

    private static int time = 0;

    private int currentPosition1 = 0;
    private int currentPosition2 = 0;
    private int currentPosition3 = 0;
    private int currentPosition4 = 0;

    private boolean isFirst;

    private boolean isRecording1 = false;
    private boolean isRecording2 = false;
    private boolean isRecording3 = false;
    private boolean isRecording4 = false;

    private boolean isRecorded1 = false;
    private boolean isRecorded2 = false;
    private boolean isRecorded3 = false;
    private boolean isRecorded4 = false;

    private boolean isPlaying1 = false;
    private boolean isPlaying2 = false;
    private boolean isPlaying3 = false;
    private boolean isPlaying4 = false;

    private Timer stopNonFirstRecord;
    private Timer loop;

    private ArrayList<String> track1 = new ArrayList<String>();
    private ArrayList<String> track2 = new ArrayList<String>();
    private ArrayList<String> track3 = new ArrayList<String>();
    private ArrayList<String> track4 = new ArrayList<String>();

    private int trackNum1 = 0;
    private int trackNum2 = 0;
    private int trackNum3 = 0;
    private int trackNum4 = 0;

    private static String trackName1;
    private static String trackName2;
    private static String trackName3;
    private static String trackName4;

    private ProgressBar progressBar1;
    private ProgressBar progressBar2;
    private ProgressBar progressBar3;
    private ProgressBar progressBar4;

    private boolean progress1Running = false;
    private boolean progress2Running = false;
    private boolean progress3Running = false;
    private boolean progress4Running = false;

    private int progress1 = 0;
    private int progress2 = 0;
    private int progress3 = 0;
    private int progress4 = 0;

    private Handler mHandler = new Handler();


    private boolean track1Selected = false;
    private boolean track2Selected = false;
    private boolean track3Selected = false;
    private boolean track4Selected = false;

    private void initProgressBar(){
        progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);
        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);
        progressBar3 = (ProgressBar) findViewById(R.id.progressBar3);
        progressBar4 = (ProgressBar) findViewById(R.id.progressBar4);
    }

    private void startProgressBar1(){
        progress1Running = true;
        progressBar1.setVisibility(View.VISIBLE);
        progress1 = 0;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progress1Running && progress1 <= 100){
                    ++progress1;
                    android.os.SystemClock.sleep(time / 100);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar1.setProgress(progress1);
                        }
                    });

                    if (progress1 == 100){
                        progress1 = 0;
                        android.os.SystemClock.sleep(time / 100);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                progressBar1.setProgress(progress1);
                            }
                        });
                    }
                }
            }
        }).start();
    }

    private void startProgressBar2(){
        progress2Running = true;
        progressBar2.setVisibility(View.VISIBLE);
        progress2 = 0;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progress2Running && progress2 <= 100){
                    ++progress2;
                    android.os.SystemClock.sleep(time / 100);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar2.setProgress(progress2);
                        }
                    });

                    if (progress2 == 100){
                        progress2 = 0;
                        android.os.SystemClock.sleep(time / 100);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                progressBar2.setProgress(progress2);
                            }
                        });
                    }
                }
            }
        }).start();
    }

    private void startProgressBar3(){
        progress3Running = true;
        progressBar3.setVisibility(View.VISIBLE);
        progress3 = 0;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progress3Running && progress3 <= 100){
                    ++progress3;
                    android.os.SystemClock.sleep(time / 100);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar3.setProgress(progress3);
                        }
                    });

                    if (progress3 == 100){
                        progress3 = 0;
                        android.os.SystemClock.sleep(time / 100);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                progressBar3.setProgress(progress3);
                            }
                        });
                    }
                }
            }
        }).start();
    }

    private void startProgressBar4(){
        progress4Running = true;
        progressBar4.setVisibility(View.VISIBLE);
        progress4 = 0;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progress4Running && progress4 <= 100){
                    ++progress4;
                    android.os.SystemClock.sleep(time / 100);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar4.setProgress(progress4);
                        }
                    });

                    if (progress4 == 100){
                        progress4 = 0;
                        android.os.SystemClock.sleep(time / 100);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                progressBar4.setProgress(progress4);
                            }
                        });
                    }
                }
            }
        }).start();
    }

    private void resetProgressBar1() {
        progress1Running = false;
        progressBar1.setVisibility(View.INVISIBLE);
        progress1 = 0;
    }

    private void resetProgressBar2() {
        progress2Running = false;
        progressBar2.setVisibility(View.INVISIBLE);
        progress2 = 0;
    }

    private void resetProgressBar3() {
        progress3Running = false;
        progressBar3.setVisibility(View.INVISIBLE);
        progress3 = 0;
    }

    private void resetProgressBar4() {
        progress4Running = false;
        progressBar4.setVisibility(View.INVISIBLE);
        progress4 = 0;
    }

    private void disableTrackButtons(int currentTrack){
        if (currentTrack == 1){
            card2.setEnabled(false);
            card3.setEnabled(false);
            card4.setEnabled(false);

            card2.setCardBackgroundColor(Color.parseColor("#bababa"));
            card3.setCardBackgroundColor(Color.parseColor("#bababa"));
            card4.setCardBackgroundColor(Color.parseColor("#bababa"));
        }

        if (currentTrack == 2){
            card1.setEnabled(false);
            card3.setEnabled(false);
            card4.setEnabled(false);

            card1.setCardBackgroundColor(Color.parseColor("#bababa"));
            card3.setCardBackgroundColor(Color.parseColor("#bababa"));
            card4.setCardBackgroundColor(Color.parseColor("#bababa"));
        }

        if (currentTrack == 3){
            card1.setEnabled(false);
            card2.setEnabled(false);
            card4.setEnabled(false);

            card1.setCardBackgroundColor(Color.parseColor("#bababa"));
            card2.setCardBackgroundColor(Color.parseColor("#bababa"));
            card4.setCardBackgroundColor(Color.parseColor("#bababa"));
        }

        if (currentTrack == 4){
            card2.setEnabled(false);
            card3.setEnabled(false);
            card1.setEnabled(false);

            card2.setCardBackgroundColor(Color.parseColor("#bababa"));
            card3.setCardBackgroundColor(Color.parseColor("#bababa"));
            card1.setCardBackgroundColor(Color.parseColor("#bababa"));
        }
    }

    private void enableTrackButtons(int currentTrack){
        if (currentTrack == 1){
            card2.setEnabled(true);
            card3.setEnabled(true);
            card4.setEnabled(true);

            card2.setCardBackgroundColor(Color.parseColor("#ffffff"));
            card3.setCardBackgroundColor(Color.parseColor("#ffffff"));
            card4.setCardBackgroundColor(Color.parseColor("#ffffff"));
        }

        if (currentTrack == 2){
            card1.setEnabled(true);
            card3.setEnabled(true);
            card4.setEnabled(true);

            card1.setCardBackgroundColor(Color.parseColor("#ffffff"));
            card3.setCardBackgroundColor(Color.parseColor("#ffffff"));
            card4.setCardBackgroundColor(Color.parseColor("#ffffff"));
        }

        if (currentTrack == 3){
            card1.setEnabled(true);
            card2.setEnabled(true);
            card4.setEnabled(true);

            card1.setCardBackgroundColor(Color.parseColor("#ffffff"));
            card2.setCardBackgroundColor(Color.parseColor("#ffffff"));
            card4.setCardBackgroundColor(Color.parseColor("#ffffff"));
        }

        if (currentTrack == 4){
            card2.setEnabled(true);
            card3.setEnabled(true);
            card1.setEnabled(true);

            card2.setCardBackgroundColor(Color.parseColor("#ffffff"));
            card3.setCardBackgroundColor(Color.parseColor("#ffffff"));
            card1.setCardBackgroundColor(Color.parseColor("#ffffff"));
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted ) finish();
    }

    private void initCards(){
        card1 = (CardView) this.findViewById(R.id.cardOne);
        card2 = (CardView) this.findViewById(R.id.cardTwo);
        card3 = (CardView) this.findViewById(R.id.cardThree);
        card4 = (CardView) this.findViewById(R.id.cardFour);

        cardImage1 = (ImageView) this.findViewById(R.id.cardImage1);
        cardImage2 = (ImageView) this.findViewById(R.id.cardImage2);
        cardImage3 = (ImageView) this.findViewById(R.id.cardImage3);
        cardImage4 = (ImageView) this.findViewById(R.id.cardImage4);

        micResId = getResources().getIdentifier("mic", "drawable","com.example.loopit");
        recordResId = getResources().getIdentifier("record", "drawable","com.example.loopit");
        pauseResId = getResources().getIdentifier("pause", "drawable","com.example.loopit");
        playResId = getResources().getIdentifier("play", "drawable","com.example.loopit");

        cardImage1.setImageResource(micResId);
        cardImage2.setImageResource(micResId);
        cardImage3.setImageResource(micResId);
        cardImage4.setImageResource(micResId);

        cardImage1.setVisibility(View.VISIBLE);
        cardImage2.setVisibility(View.VISIBLE);
        cardImage3.setVisibility(View.VISIBLE);
        cardImage4.setVisibility(View.VISIBLE);

        trackControl1 = (CardView) this.findViewById(R.id.control1);
        trackControl2 = (CardView) this.findViewById(R.id.control2);
        trackControl3 = (CardView) this.findViewById(R.id.control3);
        trackControl4 = (CardView) this.findViewById(R.id.control4);

        allTracks = (CardView) this.findViewById(R.id.control5);

        clearButton = (CardView) this.findViewById(R.id.clearTrack);
        overdubButton = (CardView) this.findViewById(R.id.overdub);
        playButton = (CardView) this.findViewById(R.id.playAll);
    }

    private void initFloatingButtons() {
        com.getbase.floatingactionbutton.FloatingActionButton fabMetronome = findViewById(R.id.fabMetronome);
        com.getbase.floatingactionbutton.FloatingActionButton fabSettings = findViewById(R.id.fabSettings);

        fabMetronome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PopUpMetronome.class));
            }
        });

        fabSettings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PopUpSettings.class));
            }
        });
    }

    public void start_record1(){
        if (isFirst){
            trackName1 = getExternalCacheDir().getAbsolutePath();
            trackName1 += "/track1"+ trackNum1 +".ogg";
            recorder1 = new Recorder(trackName1);
            track1.add(trackName1);
            trackNum1 += 1;
            recorder1.start();
        }
        else{
            trackName1 = getExternalCacheDir().getAbsolutePath();
            trackName1 += "/track1"+ trackNum1 +".ogg";
            recorder1 = new Recorder(trackName1);
            track1.add(trackName1);
            trackNum1 += 1;
            recorder1.start();
            stopNonFirstRecord = new Timer();
            stopNonFirstRecord.schedule(new TimerTask() {
                @Override
                public void run() {
                    recorder1.stop();
//                    player1.play();
                    play1();
                    isPlaying1 = true;
                    isRecorded1 = true;
                    isRecording1 = false;
                    cardImage1.setImageResource(pauseResId);
                }
            }, time+220);
        }
    }

    public void start_overdub1(){
        trackName1 = getExternalCacheDir().getAbsolutePath();
        trackName1 += "/track1"+ trackNum1 +".ogg";
        overdub1 = new Overdub(trackName1, time);
        track1.add(trackName1);
        trackNum1 += 1;
        overdub1.start();
        stopNonFirstRecord = new Timer();
        stopNonFirstRecord.schedule(new TimerTask() {
            @Override
            public void run() {
//                player1.playPrevious();
//                overdubPlayer1.play();
                play1();
                isPlaying1 = true;
                isRecorded1 = true;
                isRecording1 = false;
                cardImage1.setImageResource(pauseResId);
            }
        }, time+220);
    }

//    public void start_overdub1(){
//        player1.pause();
//        currentPosition1 = player1.getCurrentPosition();
//        player1.play();
//        trackName1 = getExternalCacheDir().getAbsolutePath();
//        trackName1 += "/track1"+ trackNum1 +".ogg";
//        track1.add(trackName1);
//        trackNum1 += 1;
//        overdubHandler = new Timer();
//        playerHandler = new Timer();
//        overdubHandler.schedule(startOverdub1, currentPosition1);
//        playerHandler.schedule(playOverdub1, time+currentPosition1+500);
//    }
//
//    private TimerTask startOverdub1 = new TimerTask() {
//        public void run(){
//            player1.stop();
//            player1.playPrevious();
//            overdub1 = new Overdub(trackName1, time);
//            overdub1.start();
//        }
//    };
//
//    private TimerTask playOverdub1 = new TimerTask() {
//        public void run(){
//            player1.stop();
//            player1 = new Player(track1, isLoop);
//            player1.play();
//        }
//    };

    public void stop_record1(){
        recorder1.stop();
        player1.play();
        time = player1.getDuration();
        loop = new Timer();
        loop.schedule(new TimerTask() {
            @Override
            public void run() {
                player1.play();
            }
        }, time-180, time-180);
    }

    public void play1(){
        if(trackNum1 == 1){
            player1.play();
            loop = new Timer();
            loop.schedule(new TimerTask() {
                @Override
                public void run() {
                    player1.play();
                }
            }, time-195, time-215);
        }
        else{
            player1.playPrevious();
            overdubPlayer1.play();
            loop = new Timer();
            loop.schedule(new TimerTask() {
                @Override
                public void run() {
                    player1.playPrevious();
                    overdubPlayer1.play();
                }
            }, time-195, time-215);
        }
    }

    public void stop1(){
        loop.cancel();
        loop.purge();
        player1.stop();
        overdubPlayer1.stop();
    }

    public void start_record2(){
        if (isFirst){
            trackName2 = getExternalCacheDir().getAbsolutePath();
            trackName2 += "/track2"+ trackNum2 +".ogg";
            recorder2 = new Recorder(trackName2);
            track2.add(trackName2);
            trackNum2 += 1;
            recorder2.start();
        }
        else{
            trackName2 = getExternalCacheDir().getAbsolutePath();
            trackName2 += "/track2"+ trackNum2 +".ogg";
            recorder2 = new Recorder(trackName2);
            track2.add(trackName2);
            trackNum2 += 1;
            recorder2.start();
            stopNonFirstRecord = new Timer();
            stopNonFirstRecord.schedule(new TimerTask() {
                @Override
                public void run() {
                    recorder2.stop();
//                    player2.play();
                    play2();
                    isPlaying2 = true;
                    isRecorded2 = true;
                    isRecording2 = false;
                    cardImage2.setImageResource(pauseResId);
                }
            }, time+220);
        }
    }

    public void stop_record2(){
        recorder2.stop();
        player2.play();
        time = player2.getDuration();
        loop = new Timer();
        loop.schedule(new TimerTask() {
            @Override
            public void run() {
                player2.play();
            }
        }, time-180, time-180);
    }

    public void start_overdub2(){
        trackName2 = getExternalCacheDir().getAbsolutePath();
        trackName2 += "/track2"+ trackNum2 +".ogg";
        overdub2 = new Overdub(trackName2, time);
        track2.add(trackName2);
        trackNum2 += 1;
        overdub2.start();
        stopNonFirstRecord = new Timer();
        stopNonFirstRecord.schedule(new TimerTask() {
            @Override
            public void run() {
//                player2.playPrevious();
//                overdubPlayer2.play();
                play2();
                isPlaying2 = true;
                isRecorded2 = true;
                isRecording2 = false;
                cardImage2.setImageResource(pauseResId);
            }
        }, time+220);
    }

    public void play2(){
        if(trackNum2 == 1){
            player2.play();
            loop = new Timer();
            loop.schedule(new TimerTask() {
                @Override
                public void run() {
                    player2.play();
                }
            }, time-180, time-280);
        }
        else{
            player2.playPrevious();
            overdubPlayer2.play();
            loop = new Timer();
            loop.schedule(new TimerTask() {
                @Override
                public void run() {
                    player2.playPrevious();
                    overdubPlayer2.play();
                }
            }, time-180, time-180);
        }
    }

    public void stop2(){
        loop.cancel();
        loop.purge();
        player2.stop();
        overdubPlayer2.stop();
    }

    public void start_record3(){
        if (isFirst){
            trackName3 = getExternalCacheDir().getAbsolutePath();
            trackName3 += "/track3"+ trackNum3 +".ogg";
            recorder3 = new Recorder(trackName3);
            track3.add(trackName3);
            trackNum3 += 1;
            recorder3.start();
        }
        else{
            trackName3 = getExternalCacheDir().getAbsolutePath();
            trackName3 += "/track3"+ trackNum3 +".ogg";
            recorder3 = new Recorder(trackName3);
            track3.add(trackName3);
            trackNum3 += 1;
            recorder3.start();
            stopNonFirstRecord = new Timer();
            stopNonFirstRecord.schedule(new TimerTask() {
                @Override
                public void run() {
                    recorder3.stop();
//                    player3.play();
                    play3();
                    isPlaying3 = true;
                    isRecorded3 = true;
                    isRecording3 = false;
                    cardImage3.setImageResource(pauseResId);
                }
            }, time+220);
        }
    }

    public void stop_record3(){
        recorder3.stop();
        player3.play();
        time = player3.getDuration();
        loop = new Timer();
        loop.schedule(new TimerTask() {
            @Override
            public void run() {
                player3.play();
            }
        }, time-180, time-180);
    }

    public void start_overdub3(){
        trackName3 = getExternalCacheDir().getAbsolutePath();
        trackName3 += "/track3"+ trackNum3 +".ogg";
        overdub3 = new Overdub(trackName3, time);
        track3.add(trackName3);
        trackNum3 += 1;
        overdub3.start();
        stopNonFirstRecord = new Timer();
        stopNonFirstRecord.schedule(new TimerTask() {
            @Override
            public void run() {
//                player3.playPrevious();
//                overdubPlayer3.play();
                play3();
                isPlaying3 = true;
                isRecorded3 = true;
                isRecording3 = false;
                cardImage3.setImageResource(pauseResId);
            }
        }, time+220);
    }

    public void play3(){
        if(trackNum3 == 1){
            player3.play();
            loop = new Timer();
            loop.schedule(new TimerTask() {
                @Override
                public void run() {
                    player3.play();
                }
            }, time-180, time-280);
        }
        else{
            player3.playPrevious();
            overdubPlayer3.play();
            loop = new Timer();
            loop.schedule(new TimerTask() {
                @Override
                public void run() {
                    player3.playPrevious();
                    overdubPlayer3.play();
                }
            }, time-180, time-180);
        }
    }

    public void stop3(){
        loop.cancel();
        loop.purge();
        player3.stop();
        overdubPlayer3.stop();
    }

    public void start_record4(){
        if (isFirst){
            trackName4 = getExternalCacheDir().getAbsolutePath();
            trackName4 += "/track4"+ trackNum4 +".ogg";
            recorder4 = new Recorder(trackName4);
            track4.add(trackName4);
            trackNum4 += 1;
            recorder4.start();
        }
        else{
            trackName4 = getExternalCacheDir().getAbsolutePath();
            trackName4 += "/track4"+ trackNum4 +".ogg";
            track4.add(trackName4);
            recorder4 = new Recorder(trackName4);
            trackNum4 += 1;
            recorder4.start();
            stopNonFirstRecord = new Timer();
            stopNonFirstRecord.schedule(new TimerTask() {
                @Override
                public void run() {
                    recorder4.stop();
//                    player4.play();
                    play4();
                    isPlaying4 = true;
                    isRecorded4 = true;
                    isRecording4 = false;
                    cardImage4.setImageResource(pauseResId);
                }
            }, time+220);
        }
    }

    public void stop_record4(){
        recorder4.stop();
        player4.play();
        time = player4.getDuration();
        loop = new Timer();
        loop.schedule(new TimerTask() {
            @Override
            public void run() {
                player4.play();
            }
        }, time-180, time-180);
    }

    public void start_overdub4(){
        trackName4 = getExternalCacheDir().getAbsolutePath();
        trackName4 += "/track4"+ trackNum1 +".ogg";
        overdub4 = new Overdub(trackName4, time);
        track4.add(trackName1);
        trackNum4 += 1;
        overdub4.start();
        stopNonFirstRecord = new Timer();
        stopNonFirstRecord.schedule(new TimerTask() {
            @Override
            public void run() {
//                player4.playPrevious();
//                overdubPlayer4.play();
                play4();
                isPlaying4 = true;
                isRecorded4 = true;
                isRecording4 = false;
                cardImage4.setImageResource(pauseResId);
            }
        }, time+220);
    }

    public void play4(){
        if(trackNum4 == 1){
            player4.play();
            loop = new Timer();
            loop.schedule(new TimerTask() {
                @Override
                public void run() {
                    player4.play();
                }
            }, time-180, time-280);
        }
        else{
            player4.playPrevious();
            overdubPlayer4.play();
            loop = new Timer();
            loop.schedule(new TimerTask() {
                @Override
                public void run() {
                    player4.playPrevious();
                    overdubPlayer4.play();
                }
            }, time-180, time-180);
        }
    }

    public void stop4(){
        loop.cancel();
        loop.purge();
        player4.stop();
        overdubPlayer4.stop();
    }

    private void initLabels () {
        cardImage1.setContentDescription("Record / Pause / Play");
        cardImage2.setContentDescription("Record / Pause / Play");
        cardImage3.setContentDescription("Record / Pause / Play");
        cardImage4.setContentDescription("Record / Pause / Play");

        TextView controlPanel = (TextView) findViewById(R.id.controlPanel);
        TextView operations = (TextView) findViewById(R.id.operations);
        TextView track1 = (TextView) findViewById(R.id.track1);
        TextView track2 = (TextView) findViewById(R.id.track2);
        TextView track3 = (TextView) findViewById(R.id.track3);
        TextView track4 = (TextView) findViewById(R.id.track4);
        TextView clearText = (TextView) findViewById(R.id.clearText);
        TextView overdubText = (TextView) findViewById(R.id.overdubText);
        TextView toneText = (TextView) findViewById(R.id.toneText);
        TextView pitchText = (TextView) findViewById(R.id.pitchText);

        TextView allTracks = (TextView) findViewById(R.id.allTracks);
        TextView playTracks = (TextView) findViewById(R.id.playAllText);

        controlPanel.setText("Control Panel");
        operations.setText("Operations");

        track1.setText("Track 1");
        track2.setText("Track 2");
        track3.setText("Track 3");
        track4.setText("Track 4");

        allTracks.setText("All");
        playTracks.setText("Play");

        clearText.setText("Clear");
        overdubText.setText("Over-Dub");
        toneText.setText("Tone");
        pitchText.setText("Pitch");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        metronome = new Metronome(initBpm, this);

        Toast toast = Toast.makeText(MainActivity.this, "Please use earphones for the best experience.", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

        initCards();
        initFloatingButtons();
        initLabels();

        initProgressBar();

        isFirst = true;

        player1 = new Player(track1, isLoop);
        player2 = new Player(track2, isLoop);
        player3 = new Player(track3, isLoop);
        player4 = new Player(track4, isLoop);

        overdubPlayer1 = new Player(track1, isLoop);
        overdubPlayer2 = new Player(track2, isLoop);
        overdubPlayer3 = new Player(track3, isLoop);
        overdubPlayer4 = new Player(track4, isLoop);

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);

        card1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
            if(!isRecording1 && !isRecorded1 && !isPlaying1){
                start_record1();
//                disableTrackButtons(1);
                isRecording1 = true;
                cardImage1.setImageResource(recordResId);
            }

            else if(isFirst && isRecording1 && !isRecorded1 && !isPlaying1){
                stop_record1();
                startProgressBar1();
//                enableTrackButtons(1);
                isFirst = false;
                isPlaying1 = true;
                isRecorded1 = true;
                isRecording1 = false;
                cardImage1.setImageResource(pauseResId);
            }

            else if(!isFirst && !isRecording1 && isRecorded1 && isPlaying1){
                stop1();
                resetProgressBar1();
//                enableTrackButtons(1);
                isPlaying1 = false;
                cardImage1.setImageResource(playResId);
            }

            else if(!isFirst && !isRecording1 && isRecorded1 && !isPlaying1){
                play1();
//                enableTrackButtons(1);
                startProgressBar1();
                isPlaying1 = true;
                cardImage1.setImageResource(pauseResId);
            }
            }
        });

        card2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
            if(!isRecording2 && !isRecorded2 && !isPlaying2){
                start_record2();
//                disableTrackButtons(2);
                isRecording2 = true;
                cardImage2.setImageResource(recordResId);
            }
            else if(isFirst && isRecording2 && !isRecorded2 && !isPlaying2){
                stop_record2();
                startProgressBar2();
//                enableTrackButtons(2);
                isFirst = false;
                isPlaying2 = true;
                isRecorded2 = true;
                isRecording2 = false;
                cardImage2.setImageResource(pauseResId);
            }
            else if(!isFirst && !isRecording2 && isRecorded2 && isPlaying2){
                stop2();
                resetProgressBar2();
//                enableTrackButtons(2);
                isPlaying2 = false;
                cardImage2.setImageResource(playResId);
            }
            else if(!isFirst && !isRecording2 && isRecorded2 && !isPlaying2){
                play2();
                startProgressBar2();
//                enableTrackButtons(2);
                isPlaying2 = true;
                cardImage2.setImageResource(pauseResId);
            }
            }
        });

        card3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
            if(!isRecording3 && !isRecorded3 && !isPlaying3){
                start_record3();
//                disableTrackButtons(3);
                isRecording3 = true;
                cardImage3.setImageResource(recordResId);
            }
            else if(isFirst && isRecording3 && !isRecorded3 && !isPlaying3){
                stop_record3();
                startProgressBar3();
//                enableTrackButtons(3);
                isFirst = false;
                isPlaying3 = true;
                isRecorded3 = true;
                isRecording3 = false;
                cardImage3.setImageResource(pauseResId);
            }
            else if(!isFirst &&!isRecording3 && isRecorded3 && isPlaying3){
                stop3();
                resetProgressBar3();
//                enableTrackButtons(3);
                isPlaying3 = false;
                cardImage3.setImageResource(playResId);
            }
            else if(!isFirst &&!isRecording3 && isRecorded3 && !isPlaying3){
                play3();
                startProgressBar3();
//                enableTrackButtons(3);
                isPlaying3 = true;
                cardImage3.setImageResource(pauseResId);
            }
            }
        });

        card4.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
            if(!isRecording4 && !isRecorded4 && !isPlaying4){
                start_record4();
//                disableTrackButtons(4);
                isRecording4 = true;
                cardImage4.setImageResource(recordResId);
            }
            else if(isFirst && isRecording4 && !isRecorded4 && !isPlaying4){
                stop_record4();
                startProgressBar4();
//                enableTrackButtons(4);
                isFirst = false;
                isPlaying4 = true;
                isRecorded4 = true;
                isRecording4 = false;
                cardImage4.setImageResource(pauseResId);
            }
            else if(!isFirst && !isRecording4 && isRecorded4 && isPlaying4){
                stop4();
                resetProgressBar4();
//                enableTrackButtons(4);
                isPlaying4 = false;
                cardImage4.setImageResource(playResId);
            }
            else if(!isFirst && !isRecording4 && isRecorded4 && !isPlaying4){
                play4();
                startProgressBar4();
//                enableTrackButtons(4);
                isPlaying4 = true;
                cardImage4.setImageResource(pauseResId);
            }
            }
        });

        trackControl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trackControlNum = 1;
//                track1Selected = !track1Selected;
//                if (track1Selected){
//                    trackControl1.setCardBackgroundColor(Color.parseColor("#bababa"));
//                }
//                else{
//                    trackControl1.setCardBackgroundColor(Color.parseColor("#ffffff"));
//                }
            }
        });

        trackControl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trackControlNum = 2;
            }
        });

        trackControl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trackControlNum = 3;
            }
        });

        trackControl4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trackControlNum = 4;
            }
        });

        allTracks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trackControlNum = 5;
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (trackControlNum){
                    case 1:
                        player1.stop();
                        trackNum1 = 0;
                        isRecording1 = false;
                        isRecorded1 = false;
                        isPlaying1 = false;
                        cardImage1.setImageResource(micResId);
                        resetProgressBar1();
                        if(!isRecorded2 && !isRecorded3 && !isRecorded4){
                            isFirst = true;
                        }
                        break;

                    case 2:
                        player2.stop();
                        trackNum2 = 0;
                        isRecording2 = false;
                        isRecorded2 = false;
                        isPlaying2 = false;
                        cardImage2.setImageResource(micResId);
                        resetProgressBar2();
                        if(!isRecorded1 && !isRecorded3 && !isRecorded4){
                            isFirst = true;
                        }
                        break;

                    case 3:
                        player3.stop();
                        trackNum3 = 0;
                        isRecording3 = false;
                        isRecorded3 = false;
                        isPlaying3 = false;
                        cardImage3.setImageResource(micResId);
                        resetProgressBar3();
                        if(!isRecorded1 && !isRecorded2 && !isRecorded4){
                            isFirst = true;
                        }
                        break;

                    case 4:
                        player4.stop();
                        trackNum4 = 0;
                        isRecording4 = false;
                        isRecorded4 = false;
                        isPlaying4 = false;
                        cardImage4.setImageResource(micResId);
                        resetProgressBar4();
                        if(!isRecorded1 && !isRecorded2 && !isRecorded3){
                            isFirst = true;
                        }
                        break;

                    case 5:
                        player1.stop();
                        player2.stop();
                        player3.stop();
                        player4.stop();
                        trackNum1 = 0;
                        trackNum2 = 0;
                        trackNum3 = 0;
                        trackNum4 = 0;
                        isRecording1 = false;
                        isRecording2 = false;
                        isRecording3 = false;
                        isRecording4 = false;
                        isRecorded1 = false;
                        isRecorded2 = false;
                        isRecorded3 = false;
                        isRecorded4 = false;
                        isPlaying1 = false;
                        isPlaying2 = false;
                        isPlaying3 = false;
                        isPlaying4 = false;
                        cardImage1.setImageResource(micResId);
                        cardImage2.setImageResource(micResId);
                        cardImage3.setImageResource(micResId);
                        cardImage4.setImageResource(micResId);
                        resetProgressBar1();
                        resetProgressBar2();
                        resetProgressBar3();
                        resetProgressBar4();
                        break;

                    default:
                        Toast toast = Toast.makeText(MainActivity.this, "Please select one of the tracks in the control panel to use the operation panel.", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        break;
                }
            }
        });

        overdubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (trackControlNum){
                    case 1:
                        if(isPlaying1){
                            stop1();
                        }
                        cardImage1.setImageResource(recordResId);
                        start_overdub1();
                        isPlaying1 = false;
                        isRecording1 = true;
                        break;
                    case 2:
                        if(isPlaying2){
                            stop2();
                        }
                        cardImage2.setImageResource(recordResId);
                        start_overdub2();
                        isPlaying2 = false;
                        isRecording2 = true;
                        break;
                    case 3:
                        if(isPlaying3){
                            stop3();
                        }
                        cardImage3.setImageResource(recordResId);
                        start_overdub3();
                        isPlaying3 = false;
                        isRecording3 = true;
                        break;
                    case 4:
                        if(isPlaying4){
                            stop4();
                        }
                        cardImage4.setImageResource(recordResId);
                        start_overdub4();
                        isPlaying4 = false;
                        isRecording4 = true;
                        break;
                    default:
                        Toast toast = Toast.makeText(MainActivity.this, "Please select track 1, 2, 3, or 4 in the control panel to use this function.", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        break;
                }
            }
        });

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPlaying1){
                    play1();
                    startProgressBar1();
                    isPlaying1 = true;
                    cardImage1.setImageResource(pauseResId);
                }
                if (!isPlaying2){
                    play2();
                    startProgressBar2();
                    isPlaying2 = true;
                    cardImage2.setImageResource(pauseResId);
                }
                if (!isPlaying3){
                    play3();
                    startProgressBar3();
                    isPlaying3 = true;
                    cardImage3.setImageResource(pauseResId);
                }
                if (!isPlaying4){
                    play4();
                    startProgressBar4();
                    isPlaying4 = true;
                    cardImage4.setImageResource(pauseResId);
                }
            }
        });

    }
}

package com.example.loopit;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
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

    CardView clearButton;
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
    private Timer overdubHandler;
    private Timer playerHandler;

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

    private void initProgressBar(){
        progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);
        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);
        progressBar3 = (ProgressBar) findViewById(R.id.progressBar3);
        progressBar4 = (ProgressBar) findViewById(R.id.progressBar4);
    }

    private void startProgressBar1(){
        new Thread(new Runnable() {
            @Override
            public void run() {

                while (progress1 < 100){
                    progress1 ++;
                    android.os.SystemClock.sleep(time / 100);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar1.setProgress(progress1);
                        }
                    });
                }
            }
        }).start();
    }

    private void startProgressBar2(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progress2 < 100 && progress2Running){
                    progress2++;
                    android.os.SystemClock.sleep(50);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar2.setProgress(progress2);
                        }
                    });
                }
            }
        }).start();
    }

    private void startProgressBar3(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progress3 < 100 && progress3Running){
                    progress3++;
                    android.os.SystemClock.sleep(50);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar3.setProgress(progress3);
                        }
                    });
                }
            }
        }).start();
    }

    private void startProgressBar4(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progress4 < 100 && progress4Running){
                    progress4++;
                    android.os.SystemClock.sleep(50);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar4.setProgress(progress4);
                        }
                    });
                }
            }
        }).start();
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

        clearButton = (CardView) this.findViewById(R.id.clearTrack);
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
        if (isFirst == true){
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
            stopNonFirstRecord.schedule(stopNonFirst, time);
        }
    }

    private TimerTask stopNonFirst = new TimerTask() {
        @Override
        public void run() {
            recorder1.stop();
            player1.play();
            isPlaying1 = true;
            isRecorded1 = true;
            isRecording1 = false;
            cardImage1.setImageResource(pauseResId);
        }
    };

    public void start_overdub1(){
        player1.pause();
        currentPosition1 = player1.getCurrentPosition();
        player1.play();
        trackName1 = getExternalCacheDir().getAbsolutePath();
        trackName1 += "/track1"+ trackNum1 +".ogg";
        track1.add(trackName1);
        trackNum1 += 1;
        overdubHandler = new Timer();
        playerHandler = new Timer();
        overdubHandler.schedule(startOverdub1, currentPosition1);
        playerHandler.schedule(playOverdub1, time+currentPosition1+500);
    }

    private TimerTask startOverdub1 = new TimerTask() {
        public void run(){
            player1.stop();
            player1.playPrevious();
            overdub1 = new Overdub(trackName1, time);
            overdub1.start();
        }
    };

    private TimerTask playOverdub1 = new TimerTask() {
        public void run(){
            player1.stop();
            player1 = new Player(track1, isLoop);
            player1.play();
        }
    };

    public void stop_record1(){
        recorder1.stop();
        player1.play();
        time = player1.getDuration();
    }

    public void play1(){
        player1.play();
    }

    public void stop1(){
        player1.stop();
    }

    public void start_record2(){
        if (isFirst == true){
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
            stopNonFirstRecord.schedule(stopNonFirst2, time);
        }
    }

    private TimerTask stopNonFirst2 = new TimerTask() {
        @Override
        public void run() {
            recorder2.stop();
            player2.play();
            isPlaying2 = true;
            isRecorded2 = true;
            isRecording2 = false;
            cardImage2.setImageResource(pauseResId);
        }
    };

    public void start_overdub2(){
        player2.pause();
        currentPosition2 = player2.getCurrentPosition();
        player2.play();
        trackName2 = getExternalCacheDir().getAbsolutePath();
        trackName2 += "/track2"+ trackNum2 +".ogg";
        track2.add(trackName2);
        trackNum2 += 1;
        overdubHandler = new Timer();
        playerHandler = new Timer();
        overdubHandler.schedule(startOverdub2, currentPosition2);
        playerHandler.schedule(playOverdub2, time+currentPosition2+500);
    }

    private TimerTask startOverdub2 = new TimerTask() {
        public void run(){
            player2.stop();
            player2.playPrevious();
            overdub2 = new Overdub(trackName2, time);
            overdub2.start();
        }
    };

    private TimerTask playOverdub2 = new TimerTask() {
        public void run(){
            player2.stop();
            player2 = new Player(track2, isLoop);
            player2.play();
        }
    };

    public void stop_record2(){
        recorder2.stop();
        player2.play();
        time = player2.getDuration();
    }

    public void play2(){
        player2.play();
    }

    public void stop2(){
        player2.stop();
    }

    public void start_record3(){
        if (isFirst == true){
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
            stopNonFirstRecord.schedule(stopNonFirst3, time);
        }
    }

    private TimerTask stopNonFirst3 = new TimerTask() {
        @Override
        public void run() {
            recorder3.stop();
            player3.play();
            isPlaying3 = true;
            isRecorded3 = true;
            isRecording3 = false;
            cardImage3.setImageResource(pauseResId);
        }
    };

    public void start_overdub3(){
        player3.pause();
        currentPosition3 = player3.getCurrentPosition();
        player3.play();
        trackName3 = getExternalCacheDir().getAbsolutePath();
        trackName3 += "/track3"+ trackNum3 +".ogg";
        track3.add(trackName3);
        trackNum3 += 1;
        overdubHandler = new Timer();
        playerHandler = new Timer();
        overdubHandler.schedule(startOverdub3, currentPosition3);
        playerHandler.schedule(playOverdub3, time+currentPosition3+500);
    }

    private TimerTask startOverdub3 = new TimerTask() {
        public void run(){
            player3.stop();
            player3.playPrevious();
            overdub3 = new Overdub(trackName3, time);
            overdub3.start();
        }
    };

    private TimerTask playOverdub3 = new TimerTask() {
        public void run(){
            player3.stop();
            player3 = new Player(track3, isLoop);
            player3.play();
        }
    };

    public void stop_record3(){
        recorder3.stop();
        player3.play();
        time = player3.getDuration();
    }

    public void play3(){
        player3.play();
    }

    public void stop3(){
        player3.stop();
    }

    public void start_record4(){
        if (isFirst == true){
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
            stopNonFirstRecord.schedule(stopNonFirst4, time);
        }
    }

    private TimerTask stopNonFirst4 = new TimerTask() {
        @Override
        public void run() {
            recorder4.stop();
            player4.play();
            isPlaying4 = true;
            isRecorded4 = true;
            isRecording4 = false;
            cardImage4.setImageResource(pauseResId);
        }
    };

    public void start_overdub4(){
        player4.pause();
        currentPosition4 = player4.getCurrentPosition();
        player4.play();
        trackName4 = getExternalCacheDir().getAbsolutePath();
        trackName4 += "/track4"+ trackNum4 +".ogg";
        track4.add(trackName4);
        trackNum4 += 1;
        overdubHandler = new Timer();
        playerHandler = new Timer();
        overdubHandler.schedule(startOverdub4, currentPosition4);
        playerHandler.schedule(playOverdub4, time+currentPosition4+500);
    }

    private TimerTask startOverdub4 = new TimerTask() {
        public void run(){
            player4.stop();
            player4.playPrevious();
            overdub4 = new Overdub(trackName4, time);
            overdub4.start();
        }
    };

    private TimerTask playOverdub4 = new TimerTask() {
        public void run(){
            player4.stop();
            player4 = new Player(track4, isLoop);
            player4.play();
        }
    };

    public void stop_record4(){
        recorder4.stop();
        player4.play();
        time = player4.getDuration();
    }

    public void play4(){
        player4.play();
    }

    public void stop4(){
        player4.stop();
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

        controlPanel.setText("Control Panel");
        operations.setText("Operations");

        track1.setText("Track 1");
        track2.setText("Track 2");
        track3.setText("Track 3");
        track4.setText("Track 4");

        clearText.setText("Clear");
        overdubText.setText("Over-Dub");
        toneText.setText("Tone");
        pitchText.setText("Pitch");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initCards();
        initFloatingButtons();
        initLabels();

        initProgressBar();
        isFirst = true;

        player1 = new Player(track1, isLoop);
        player2 = new Player(track2, isLoop);
        player3 = new Player(track3, isLoop);
        player4 = new Player(track4, isLoop);

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);

        card1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
            if(!isRecording1 && !isRecorded1 && !isPlaying1){
                start_record1();
                isRecording1 = true;
                cardImage1.setImageResource(recordResId);
            }

            else if(isFirst && isRecording1 && !isRecorded1 && !isPlaying1){
                stop_record1();
                isFirst = false;
                isPlaying1 = true;
                isRecorded1 = true;
                isRecording1 = false;
                cardImage1.setImageResource(pauseResId);
            }

            else if(!isFirst && !isRecording1 && isRecorded1 && isPlaying1){
                stop1();
                isPlaying1 = false;
                cardImage1.setImageResource(playResId);
            }

            else if(!isFirst && !isRecording1 && isRecorded1 && !isPlaying1){
                play1();
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
                isRecording2 = true;
                cardImage2.setImageResource(recordResId);
            }
            else if(isFirst && isRecording2 && !isRecorded2 && !isPlaying2){
                stop_record2();
                isFirst = false;
                isPlaying2 = true;
                isRecorded2 = true;
                isRecording2 = false;
                cardImage2.setImageResource(pauseResId);
            }
            else if(!isFirst && !isRecording2 && isRecorded2 && isPlaying2){
                stop2();
                isPlaying2 = false;
                cardImage2.setImageResource(playResId);
            }
            else if(!isFirst && !isRecording2 && isRecorded2 && !isPlaying2){
                play2();
                isPlaying2 = true;
                cardImage2.setImageResource(pauseResId);
            }
            }
        });

        card3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
            if(!isRecording3 && !isRecorded3 && !isPlaying3){
                start_record3();
                isRecording3 = true;
                cardImage3.setImageResource(recordResId);
            }
            else if(isFirst && isRecording3 && !isRecorded3 && !isPlaying3){
                stop_record3();
                isFirst = false;
                isPlaying3 = true;
                isRecorded3 = true;
                isRecording3 = false;
                cardImage3.setImageResource(pauseResId);
            }
            else if(!isFirst &&!isRecording3 && isRecorded3 && isPlaying3){
                stop3();
                isPlaying3 = false;
                cardImage3.setImageResource(playResId);
            }
            else if(!isFirst &&!isRecording3 && isRecorded3 && !isPlaying3){
                play3();
                isPlaying3 = true;
                cardImage3.setImageResource(pauseResId);
            }
            }
        });

        card4.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
            if(!isRecording4 && !isRecorded4 && !isPlaying4){
                start_record4();
                isRecording4 = true;
                cardImage4.setImageResource(recordResId);
            }
            else if(isFirst &&isRecording4 && !isRecorded4 && !isPlaying4){
                stop_record4();
                isFirst = false;
                isPlaying4 = true;
                isRecorded4 = true;
                isRecording4 = false;
                cardImage4.setImageResource(pauseResId);
            }
            else if(!isFirst && !isRecording4 && isRecorded4 && isPlaying4){
                stop4();
                isPlaying4 = false;
                cardImage4.setImageResource(playResId);
            }
            else if(!isFirst && !isRecording4 && isRecorded4 && !isPlaying4){
                play4();
                isPlaying4 = true;
                cardImage4.setImageResource(pauseResId);
            }
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trackNum1 = 0;
                trackNum2 = 0;
                trackNum3 = 0;
                trackNum4 = 0;
                isFirst = true;
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
            }
        });
    }
}

package com.example.loopit;

import android.content.Intent;
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

    ProgressBar progressBar1;
    ProgressBar progressBar2;
    ProgressBar progressBar3;
    ProgressBar progressBar4;

    int micResId;
    int recordResId;
    int pauseResId;
    int playResId;

//    CardView overdubCard1;
//    CardView overdubCard2;
//    CardView overdubCard3;
//    CardView overdubCard4;

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

    private static int time1 = 0;
    private static int time2 = 0;
    private static int time3 = 0;
    private static int time4 = 0;

    private int currentPosition1 = 0;
    private int currentPosition2 = 0;
    private int currentPosition3 = 0;
    private int currentPosition4 = 0;

    private boolean isFirst1;
    private boolean isFirst2;
    private boolean isFirst3;
    private boolean isFirst4;

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

    private Timer overdubHandler1;
    private Timer overdubHandler2;
    private Timer overdubHandler3;
    private Timer overdubHandler4;

    private Timer playerHandler1;
    private Timer playerHandler2;
    private Timer playerHandler3;
    private Timer playerHandler4;

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

        progressBar1 = (ProgressBar) this.findViewById(R.id.progressBar1);
        progressBar2 = (ProgressBar) this.findViewById(R.id.progressBar2);
        progressBar3 = (ProgressBar) this.findViewById(R.id.progressBar3);
        progressBar4 = (ProgressBar) this.findViewById(R.id.progressBar4);

        micResId = getResources().getIdentifier("mic", "drawable","com.example.loopit");
        recordResId = getResources().getIdentifier("record", "drawable","com.example.loopit");
        pauseResId = getResources().getIdentifier("pause", "drawable","com.example.loopit");
        playResId = getResources().getIdentifier("play", "drawable","com.example.loopit");

        cardImage1.setImageResource(micResId);
        cardImage2.setImageResource(recordResId);
        cardImage3.setImageResource(pauseResId);
        cardImage4.setImageResource(playResId);

        cardImage1.setVisibility(View.VISIBLE);
        cardImage2.setVisibility(View.VISIBLE);
        cardImage3.setVisibility(View.VISIBLE);
        cardImage4.setVisibility(View.VISIBLE);

        progressBar2.setVisibility(View.VISIBLE);
        progressBar2.setProgress(25);

        progressBar3.setVisibility(View.VISIBLE);
        progressBar3.setProgress(50);

        progressBar4.setVisibility(View.VISIBLE);
        progressBar4.setProgress(75);

//        overdubCard1 = (ImageView) this.findViewById(R.id.overdub);
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
        if (isFirst1 == true){
            trackName1 = getExternalCacheDir().getAbsolutePath();
            trackName1 += "/track1"+ trackNum1 +".3gp";
            recorder1 = new Recorder(trackName1);
            track1.add(trackName1);
            trackNum1 += 1;
            recorder1.start();
        }
        else{
            player1.pause();
            currentPosition1 = player1.getCurrentPosition();
            player1.play();
            trackName1 = getExternalCacheDir().getAbsolutePath();
            trackName1 += "/track1"+ trackNum1 +".3gp";
            track1.add(trackName1);
            trackNum1 += 1;
            overdubHandler1 = new Timer();
            playerHandler1 = new Timer();
            overdubHandler1.schedule(startOverdub, currentPosition1);
            playerHandler1.schedule(playOverdub, time1+currentPosition1+500);
        }
    }

    private TimerTask startOverdub = new TimerTask() {
        public void run(){
            player1.stop();
            player1.playPrevious();
            overdub1 = new Overdub(trackName1, time1);
            overdub1.start();
        }
    };

    private TimerTask playOverdub = new TimerTask() {
        public void run(){
            player1.stop();
            player1 = new Player(track1, isLoop);
            player1.play();
        }
    };

    public void stop_record1(){
        recorder1.stop();
        isFirst1 = false;
        player1.play();
        time1 = player1.getDuration();
    }

    public void play1(){
        player1.play();
    }

//    public void pause1(View v){
//        player1.pause();
//    }

    public void stop1(){
        player1.stop();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initCards();
        initFloatingButtons();

        player1 = new Player(track1, isLoop);
        isFirst1 = true;
        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);

        card1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(!isRecording1 && !isRecorded1 && !isPlaying1){
                    start_record1();
                    isRecording1 = true;
                    cardImage1.setImageResource(recordResId);
                }
                else if(isRecording1 && !isRecorded1 && !isPlaying1){
                    stop_record1();
                    isPlaying1 = true;
                    isRecorded1 = true;
                    isRecording1 = false;
                    cardImage1.setImageResource(pauseResId);
                }
                else if(!isRecording1 && isRecorded1 && isPlaying1){
                    stop1();
                    isPlaying1 = false;
                    cardImage1.setImageResource(playResId);
                }
                else if(!isRecording1 && isRecorded1 && !isPlaying1){
                    play1();
                    isPlaying1 = true;
                    cardImage1.setImageResource(pauseResId);
                }
            }
        });
//        overdubCard1.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v){
//                if(isRecorded1 && isPlaying1){
//                    start_record1();
//                }
//            }
//        });
    }
}

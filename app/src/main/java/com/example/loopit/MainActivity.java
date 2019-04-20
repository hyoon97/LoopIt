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
        cardImage2.setImageResource(micResId);
        cardImage3.setImageResource(micResId);
        cardImage4.setImageResource(micResId);

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
            overdubHandler1.schedule(startOverdub1, currentPosition1);
            playerHandler1.schedule(playOverdub1, time1+currentPosition1+500);
        }
    }

    private TimerTask startOverdub1 = new TimerTask() {
        public void run(){
            player1.stop();
            player1.playPrevious();
            overdub1 = new Overdub(trackName1, time1);
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
        isFirst1 = false;
        player1.play();
        time1 = player1.getDuration();
    }

    public void play1(){
        player1.play();
    }

    public void stop1(){
        player1.stop();
    }

    public void start_record2(){
        if (isFirst2 == true){
            trackName2 = getExternalCacheDir().getAbsolutePath();
            trackName2 += "/track2"+ trackNum2 +".3gp";
            recorder2 = new Recorder(trackName2);
            track2.add(trackName2);
            trackNum2 += 1;
            recorder2.start();
        }
        else{
            player2.pause();
            currentPosition2 = player2.getCurrentPosition();
            player2.play();
            trackName2 = getExternalCacheDir().getAbsolutePath();
            trackName2 += "/track2"+ trackNum2 +".3gp";
            track2.add(trackName2);
            trackNum2 += 1;
            overdubHandler2 = new Timer();
            playerHandler2 = new Timer();
            overdubHandler2.schedule(startOverdub2, currentPosition2);
            playerHandler2.schedule(playOverdub2, time2+currentPosition2+500);
        }
    }

    private TimerTask startOverdub2 = new TimerTask() {
        public void run(){
            player2.stop();
            player2.playPrevious();
            overdub2 = new Overdub(trackName2, time2);
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
        isFirst2 = false;
        player2.play();
        time2 = player2.getDuration();
    }

    public void play2(){
        player2.play();
    }

    public void stop2(){
        player2.stop();
    }

    public void start_record3(){
        if (isFirst3 == true){
            trackName3 = getExternalCacheDir().getAbsolutePath();
            trackName3 += "/track3"+ trackNum3 +".3gp";
            recorder3 = new Recorder(trackName3);
            track3.add(trackName3);
            trackNum3 += 1;
            recorder3.start();
        }
        else{
            player3.pause();
            currentPosition3 = player3.getCurrentPosition();
            player3.play();
            trackName3 = getExternalCacheDir().getAbsolutePath();
            trackName3 += "/track3"+ trackNum3 +".3gp";
            track3.add(trackName3);
            trackNum3 += 1;
            overdubHandler3 = new Timer();
            playerHandler3 = new Timer();
            overdubHandler3.schedule(startOverdub3, currentPosition3);
            playerHandler3.schedule(playOverdub3, time3+currentPosition3+500);
        }
    }

    private TimerTask startOverdub3 = new TimerTask() {
        public void run(){
            player3.stop();
            player3.playPrevious();
            overdub3 = new Overdub(trackName3, time3);
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
        isFirst3 = false;
        player3.play();
        time3 = player3.getDuration();
    }

    public void play3(){
        player3.play();
    }

    public void stop3(){
        player3.stop();
    }

    public void start_record4(){
        if (isFirst4 == true){
            trackName4 = getExternalCacheDir().getAbsolutePath();
            trackName4 += "/track4"+ trackNum4 +".3gp";
            recorder4 = new Recorder(trackName4);
            track4.add(trackName4);
            trackNum4 += 1;
            recorder4.start();
        }
        else{
            player4.pause();
            currentPosition4 = player4.getCurrentPosition();
            player4.play();
            trackName4 = getExternalCacheDir().getAbsolutePath();
            trackName4 += "/track4"+ trackNum4 +".3gp";
            track4.add(trackName4);
            trackNum4 += 1;
            overdubHandler4 = new Timer();
            playerHandler4 = new Timer();
            overdubHandler4.schedule(startOverdub4, currentPosition4);
            playerHandler4.schedule(playOverdub4, time4+currentPosition4+500);
        }
    }

    private TimerTask startOverdub4 = new TimerTask() {
        public void run(){
            player4.stop();
            player4.playPrevious();
            overdub4 = new Overdub(trackName4, time4);
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
        isFirst4 = false;
        player4.play();
        time4 = player4.getDuration();
    }

    public void play4(){
        player4.play();
    }

    public void stop4(){
        player4.stop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initCards();
        initFloatingButtons();

        player1 = new Player(track1, isLoop);
        isFirst1 = true;
        player2 = new Player(track2, isLoop);
        isFirst2 = true;
        player3 = new Player(track3, isLoop);
        isFirst3 = true;
        player4 = new Player(track4, isLoop);
        isFirst4 = true;
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
        card2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(!isRecording2 && !isRecorded2 && !isPlaying2){
                    start_record2();
                    isRecording2 = true;
                    cardImage2.setImageResource(recordResId);
                }
                else if(isRecording2 && !isRecorded2 && !isPlaying2){
                    stop_record2();
                    isPlaying2 = true;
                    isRecorded2 = true;
                    isRecording2 = false;
                    cardImage2.setImageResource(pauseResId);
                }
                else if(!isRecording2 && isRecorded2 && isPlaying2){
                    stop2();
                    isPlaying2 = false;
                    cardImage2.setImageResource(playResId);
                }
                else if(!isRecording2 && isRecorded2 && !isPlaying2){
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
                else if(isRecording3 && !isRecorded3 && !isPlaying3){
                    stop_record3();
                    isPlaying3 = true;
                    isRecorded3 = true;
                    isRecording3 = false;
                    cardImage3.setImageResource(pauseResId);
                }
                else if(!isRecording3 && isRecorded3 && isPlaying3){
                    stop3();
                    isPlaying3 = false;
                    cardImage3.setImageResource(playResId);
                }
                else if(!isRecording3 && isRecorded3 && !isPlaying3){
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
                else if(isRecording4 && !isRecorded4 && !isPlaying4){
                    stop_record4();
                    isPlaying4 = true;
                    isRecorded4 = true;
                    isRecording4 = false;
                    cardImage4.setImageResource(pauseResId);
                }
                else if(!isRecording4 && isRecorded4 && isPlaying4){
                    stop4();
                    isPlaying4 = false;
                    cardImage4.setImageResource(playResId);
                }
                else if(!isRecording4 && isRecorded4 && !isPlaying4){
                    play4();
                    isPlaying4 = true;
                    cardImage4.setImageResource(pauseResId);
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

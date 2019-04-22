package com.example.loopit;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.ServiceWorkerClient;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

public class PopUpMetronome extends Activity {
    private Metronome metronome;
    private int initBpm = 100;
    private Button bpmDecButton;
    private Button bpmIncButton;
    private TextView bpmTextView;
    private Switch metronomeSwitch;
    private boolean metronomeSwitchState;
    private boolean metIsPlaying = false;

    private Button decBeatsButton;
    private Button incBeatsButton;
    private TextView beatsTextView;
    private int beats = 1;
    private Switch beatsSwitch;
    private boolean beatsToggle = false;

    private Button decCountdownButton;
    private Button incCountdownButton;
    private TextView countdownTextView;
    private int countdown = 0;
    private Switch countdownSwitch;
    private boolean countdownToggle = false;

    private Button decDurationButton;
    private Button incDurationButton;
    private TextView durationTextView;
    private int duration = 0;
    private Switch durationSwitch;
    private boolean durationToggle = false;

    private int currentNote = 0;

    TextView[] pitchValues = new TextView[24];

    CardView[] pitchCards = new CardView[24];

    String[] pitches = {"C3", "Db3", "D3", "Eb3", "E3", "F3", "Gb3", "G3", "Ab3", "A3", "Bb3", "B3", "C4", "Db4", "D4", "Eb4", "E4", "F4", "Gb4", "G4", "Ab4", "A4", "Bb4", "B4"};

    private void initPitches(){

        for (int i = 0; i < pitches.length; ++i){
            String pitchId = "pitchValue" + i;
            int resId = getResources().getIdentifier(pitchId, "id", getPackageName());
            pitchValues[i] = (TextView) findViewById(resId);
            pitchValues[i].setText(pitches[i]);
            pitchValues[i].setTextColor(Color.parseColor("#ffffff"));

            String pitchCardId = "pitch" + i;
            resId = getResources().getIdentifier(pitchCardId, "id", getPackageName());
            pitchCards[i] = findViewById(resId);
            pitchCards[i].setCardBackgroundColor(Color.parseColor("#42a7f4"));
        }

        pitchCards[currentNote].setCardBackgroundColor(Color.parseColor("#f49242"));
    }


    private void initMetronome(){
        metronome = new Metronome(initBpm, this);
        bpmIncButton = (Button) findViewById(R.id.inc_bpm_button);
        bpmDecButton = (Button) findViewById(R.id.dec_bpm_button);
        bpmTextView = (TextView) findViewById(R.id.bpm_textView);
        metronomeSwitch = (Switch) findViewById(R.id.metronome_switch);

        bpmIncButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                metronome.incBpm(5);
                bpmTextView.setText("" + metronome.getBpm());
            }
        });

        bpmDecButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                metronome.decBpm(5);
                bpmTextView.setText("" + metronome.getBpm());
            }
        });

        metronomeSwitch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(metronomeSwitch.isChecked()){
                    if(!metIsPlaying){
                        metronome.run();
                    }
                } else {
                    metronome.getHandler().removeCallbacks(metronome);
                    metIsPlaying = false;
                    durationSwitch.setChecked(false);
                    countdownSwitch.setChecked(false);
                    beatsSwitch.setChecked(false);
                    durationToggle = false;
                    countdownToggle = false;
                    beatsToggle = false;
                }
            }
        });
    }

    private void initBeats() {
        decBeatsButton = (Button) findViewById(R.id.dec_beats_button);
        incBeatsButton = (Button) findViewById(R.id.inc_beats_button);
        beatsTextView = (TextView) findViewById(R.id.beats_textView);
        beatsSwitch = (Switch) findViewById(R.id.beats_switch);

        decBeatsButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(beats > 1) {
                    beats--;
                    beatsTextView.setText("" + beats);
                }
            }
        });

        incBeatsButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                beats++;
                beatsTextView.setText(""  + beats);
            }
        });

        beatsSwitch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (beatsSwitch.isChecked()){
                    beatsToggle = true;
                } else {
                    beatsToggle = false;
                }
            }
        });
    }

    private void initCountdown() {
        decCountdownButton = (Button) findViewById(R.id.dec_countdown_button);
        incCountdownButton = (Button) findViewById(R.id.inc_countdown_button);
        countdownTextView = (TextView) findViewById(R.id.countdown_textView);
        countdownSwitch = (Switch) findViewById(R.id.countdown_switch);

        decCountdownButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(countdown > 1) {
                    countdown--;
                    countdownTextView.setText("" + countdown);
                }
            }
        });

        incCountdownButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                countdown++;
                countdownTextView.setText("" + countdown);
            }
        });

        countdownSwitch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(countdownSwitch.isChecked()){
                    countdownToggle = true;
                } else {
                    countdownToggle = false;
                }
            }
        });

    }

    private void initDuration() {
        decDurationButton = (Button) findViewById(R.id.dec_duration_button);
        incDurationButton = (Button) findViewById(R.id.inc_duration_button);
        durationTextView = (TextView) findViewById(R.id.duration_textView);
        durationSwitch = (Switch) findViewById(R.id.duration_switch);

        decDurationButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (duration > 1) {
                    duration--;
                    durationTextView.setText("" + duration);
                }
            }
        });

        incDurationButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                duration++;
                durationTextView.setText("" + duration);
            }
        });

        durationSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (durationSwitch.isChecked()) {
                    durationToggle = true;
                } else {
                    durationToggle = false;
                }
            }
        });

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popupmetronome);

        DisplayMetrics dm = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;

        int height = dm.heightPixels;

        getWindow().setLayout((int)(width * .8), (int)(height * .8));

        initMetronome();
        initBeats();
        initCountdown();
        initDuration();

        initPitches();
    }
}

package com.example.loopit;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Arrays;

public class PopUpMetronome extends Activity {

    private Button bpmDecButton;
    private Button bpmIncButton;
    private TextView bpmTextView;
    private Switch metronomeSwitch;

    private Button decBeatsButton;
    private Button incBeatsButton;
    private TextView beatsTextView;
    private Switch beatsSwitch;

    private Button decCountdownButton;
    private Button incCountdownButton;
    private TextView countdownTextView;
    private Switch countdownSwitch;

    private Button decDurationButton;
    private Button incDurationButton;
    private TextView durationTextView;
    private Switch durationSwitch;

    private RadioButton clickRadio;
    private RadioButton noteRadio;



    private SeekBar durationSeekBar;

    private int currentNote = 0;

    TextView[] pitchValues = new TextView[24];

    CardView[] pitchCards = new CardView[24];

    String[] pitches = {"C3", "Db3", "D3", "Eb3", "E3", "F3", "Gb3", "G3", "Ab3", "A3", "Bb3", "B3", "C4", "Db4", "D4", "Eb4", "E4", "F4", "Gb4", "G4", "Ab4", "A4", "Bb4", "B4"};
    private String orange = "#f49242";
    private String blue = "#42a7f4";

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
            pitchCards[i].setCardBackgroundColor(Color.parseColor(blue));
        }

        for (int i = 0; i < pitches.length; i++){
            final int j = i;
            pitchCards[j].setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(MainActivity.metronomeNoteSound){
                        Log.e("Pitch", pitches[j]);
                        MainActivity.metronome.changeNote(j);
                        pitchCards[j].setCardBackgroundColor(Color.parseColor(orange));
                        pitchCards[currentNote].setCardBackgroundColor(Color.parseColor(blue));
                        currentNote = j;
                    }
                }
            });
        }
    }


    private void initMetronome(){
        bpmIncButton = (Button) findViewById(R.id.inc_bpm_button);
        bpmDecButton = (Button) findViewById(R.id.dec_bpm_button);
        bpmTextView = (TextView) findViewById(R.id.bpm_textView);
        metronomeSwitch = (Switch) findViewById(R.id.metronome_switch);

        durationSeekBar = (SeekBar) findViewById(R.id.seekbar);

        bpmIncButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                MainActivity.metronome.incBpm(5);
                bpmTextView.setText("" + MainActivity.metronome.getBpm());
            }
        });

//        bpmIncButton.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                MainActivity.metronome.incBpm(5);
//                bpmTextView.setText("" + MainActivity.metronome.getBpm());
//                return false;
//            }
//        });

        bpmDecButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                MainActivity.metronome.decBpm(5);
                bpmTextView.setText("" + MainActivity.metronome.getBpm());
            }
        });

        metronomeSwitch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(metronomeSwitch.isChecked()){
                    if(!MainActivity.metIsPlaying){
                        MainActivity.metronome.start();
                        MainActivity.metIsPlaying = true;
                    }
                } else {
                    MainActivity.metronome.stop();
                    MainActivity.metIsPlaying = false;
                    durationSwitch.setChecked(false);
                    countdownSwitch.setChecked(false);
                    beatsSwitch.setChecked(false);
                    MainActivity.durationMode = false;
                    MainActivity.countdownMode = false;
                    MainActivity.beatsMode = false;
                    pitchCards[currentNote].setCardBackgroundColor(Color.parseColor(blue));
                }
            }
        });

        metronomeSwitch.setChecked(MainActivity.metIsPlaying);
        bpmTextView.setText("" + MainActivity.metronome.getBpm());
    }

    private void initBeats() {
        decBeatsButton = (Button) findViewById(R.id.dec_beats_button);
        incBeatsButton = (Button) findViewById(R.id.inc_beats_button);
        beatsTextView = (TextView) findViewById(R.id.beats_textView);
        beatsSwitch = (Switch) findViewById(R.id.beats_switch);

        decBeatsButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(MainActivity.beats > 1) {
                    MainActivity.beats--;
                    beatsTextView.setText("" + MainActivity.beats);
                }
            }
        });

        incBeatsButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                MainActivity.beats++;
                beatsTextView.setText(""  + MainActivity.beats);
            }
        });

        beatsSwitch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (beatsSwitch.isChecked()){
                    MainActivity.beatsMode = true;
                } else {
                    MainActivity.beatsMode = false;
                }
            }
        });

        beatsSwitch.setChecked(MainActivity.beatsMode);
        beatsTextView.setText("" + MainActivity.beats);
    }

    private void initCountdown() {
        decCountdownButton = (Button) findViewById(R.id.dec_countdown_button);
        incCountdownButton = (Button) findViewById(R.id.inc_countdown_button);
        countdownTextView = (TextView) findViewById(R.id.countdown_textView);
        countdownSwitch = (Switch) findViewById(R.id.countdown_switch);

        decCountdownButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(MainActivity.countdown > 1) {
                    MainActivity.countdown--;
                    countdownTextView.setText("" + MainActivity.countdown);
                }
            }
        });

        incCountdownButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                MainActivity.countdown++;
                countdownTextView.setText("" + MainActivity.countdown);
            }
        });

        countdownSwitch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(countdownSwitch.isChecked()){
                    MainActivity.countdownMode = true;
                } else {
                    MainActivity.countdownMode = false;
                }
            }
        });

        countdownSwitch.setChecked(MainActivity.countdownMode);
        countdownTextView.setText("" + MainActivity.countdown);

    }

    private void initDuration() {
        decDurationButton = (Button) findViewById(R.id.dec_duration_button);
        incDurationButton = (Button) findViewById(R.id.inc_duration_button);
        durationTextView = (TextView) findViewById(R.id.duration_textView);
        durationSwitch = (Switch) findViewById(R.id.duration_switch);

        decDurationButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (MainActivity.duration > 1) {
                    MainActivity.duration--;
                    durationTextView.setText("" + MainActivity.duration);
                }
            }
        });

        incDurationButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                MainActivity.duration++;
                durationTextView.setText("" + MainActivity.duration);
            }
        });

        durationSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (durationSwitch.isChecked()) {
                    MainActivity.durationMode = true;
                } else {
                    MainActivity.durationMode = false;
                }
            }
        });

        durationSwitch.setChecked(MainActivity.durationMode);
        durationTextView.setText("" + MainActivity.duration);

    }

    private void initRadio(){
        clickRadio = (RadioButton) findViewById(R.id.click_radio);
        noteRadio = (RadioButton) findViewById(R.id.note_radio);

        if (MainActivity.lastMode == 1){
            clickRadio.setChecked(true);
        }

        else if (MainActivity.lastMode == 2){
            noteRadio.setChecked(true);
        }

        clickRadio.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                MainActivity.metronomeNoteSound = false;
                MainActivity.lastMode = 1;
                MainActivity.metronome.changeToClick();
                pitchCards[currentNote].setCardBackgroundColor(Color.parseColor(blue));
            }
        });

        noteRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.metronomeNoteSound = true;
                MainActivity.lastMode = 2;
                MainActivity.metronome.changeToNote();
                pitchCards[currentNote].setCardBackgroundColor(Color.parseColor(orange));
            }
        });

        clickRadio.setActivated(true);
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
        initRadio();

        initPitches();
    }
}
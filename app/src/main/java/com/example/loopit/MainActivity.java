package com.example.loopit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import android.support.v7.app.AppCompatActivity;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {



    private void initCards(){

        ImageView cardImage1 = (ImageView) this.findViewById(R.id.cardImage1);
        ImageView cardImage2 = (ImageView) this.findViewById(R.id.cardImage2);
        ImageView cardImage3 = (ImageView) this.findViewById(R.id.cardImage3);
        ImageView cardImage4 = (ImageView) this.findViewById(R.id.cardImage4);

        ProgressBar progressBar1 = (ProgressBar) this.findViewById(R.id.progressBar1);
        ProgressBar progressBar2 = (ProgressBar) this.findViewById(R.id.progressBar2);
        ProgressBar progressBar3 = (ProgressBar) this.findViewById(R.id.progressBar3);
        ProgressBar progressBar4 = (ProgressBar) this.findViewById(R.id.progressBar4);

        int micResId = getResources().getIdentifier("mic", "drawable","com.example.loopit");
        int recordResId = getResources().getIdentifier("record", "drawable","com.example.loopit");
        int pauseResId = getResources().getIdentifier("pause", "drawable","com.example.loopit");
        int playResId = getResources().getIdentifier("play", "drawable","com.example.loopit");

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initCards();

        initFloatingButtons();
    }
}

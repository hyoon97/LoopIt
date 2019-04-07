package com.example.loopit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initCards();
    }
}

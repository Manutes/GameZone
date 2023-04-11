package com.example.gamezone.remolachagame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.gamezone.R;

public class HomeScreenActivity extends AppCompatActivity {

    public MediaPlayer mp = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        mp = MediaPlayer.create(this,R.raw.banjo);
        mp.start();
    }

}
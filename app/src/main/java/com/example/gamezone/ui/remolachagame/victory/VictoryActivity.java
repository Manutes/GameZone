package com.example.gamezone.ui.remolachagame.victory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.gamezone.R;
import com.example.gamezone.databinding.ActivityVictoryBinding;
import com.example.gamezone.ui.remolachagame.homescreen.HomeScreenActivity;

public class VictoryActivity extends AppCompatActivity {

    ActivityVictoryBinding binding;
    private MediaPlayer mp = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVictoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setBackgroundGif();
        setMediaPlayer();
        setUi();
    }

    private void setUi() {
        binding.btnHomeScreen.setOnClickListener(view -> goToHomScreen());
    }

    public void goToHomScreen() {
        Intent intent = new Intent(this, HomeScreenActivity.class);
        mp.stop();
        startActivity(intent);
    }

    private void setBackgroundGif() {
        Glide.with(getBaseContext()).load(R.drawable.win3).into(binding.gif);
    }

    private void setMediaPlayer() {
        mp = MediaPlayer.create(this, R.raw.queen);
        mp.start();
    }
}
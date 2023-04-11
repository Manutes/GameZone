package com.example.gamezone.remolachagame.homescreen;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gamezone.R;
import com.example.gamezone.databinding.ActivityHomeScreenBinding;
import com.example.gamezone.remolachagame.easygame.EasyGameScreen;

public class HomeScreenActivity extends AppCompatActivity {

    private ActivityHomeScreenBinding binding;
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = ActivityHomeScreenBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setMediaPlayer();
        setButtons();
    }

    private void setMediaPlayer() {
        mp = MediaPlayer.create(this,R.raw.banjo);
        mp.start();
    }

    private void setButtons() {
        binding.btnNewEasyGame.setOnClickListener(it -> {
            Intent intent = new Intent(this, EasyGameScreen.class);
            mp.stop();
            startActivity(intent);
        });
    }

}
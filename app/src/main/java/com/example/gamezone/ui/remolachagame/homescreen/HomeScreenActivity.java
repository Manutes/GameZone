package com.example.gamezone.ui.remolachagame.homescreen;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gamezone.R;
import com.example.gamezone.databinding.ActivityHomeScreenBinding;
import com.example.gamezone.ui.remolachagame.difficultgame.DifficultGameActivity;
import com.example.gamezone.ui.remolachagame.easygame.EasyGameActivity;
import com.example.gamezone.ui.remolachagame.instructions.InstructionsFragment;

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
        if (mp != null) {
            mp.release();
        }
        mp = MediaPlayer.create(this, R.raw.banjo);
        mp.start();
        mp.setOnCompletionListener(MediaPlayer::start);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mp != null && mp.isPlaying()) {
            mp.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setMediaPlayer();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setMediaPlayer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mp != null) {
            mp.release();
            mp = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mp != null) {
            mp.release();
            mp = null;
        }
    }

    private void setButtons() {
        binding.btnNewEasyGame.setOnClickListener(it -> {
            Intent intent = new Intent(this, EasyGameActivity.class);
            mp.stop();
            startActivity(intent);
        });

        binding.btnNewDifficultGame.setOnClickListener(it -> {
            Intent intent = new Intent(this, DifficultGameActivity.class);
            mp.stop();
            startActivity(intent);
        });
        binding.btnInstructions.setOnClickListener(it -> {
            InstructionsFragment instructionsFragment = InstructionsFragment.newInstance();
            instructionsFragment.show(getSupportFragmentManager(), "intructions_fragment");
        });
    }
}
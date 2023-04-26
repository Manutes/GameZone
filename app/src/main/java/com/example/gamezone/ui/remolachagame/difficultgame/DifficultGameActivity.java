package com.example.gamezone.ui.remolachagame.difficultgame;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gamezone.databinding.ActivityDifficultGameBinding;
import com.example.gamezone.ui.remolachagame.gameover.DifficultGameOverActivity;
import com.example.gamezone.ui.remolachagame.victory.VictoryActivity;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class DifficultGameActivity extends AppCompatActivity {

    ActivityDifficultGameBinding binding;

    public DifficultGameScreen difficultGameScreen;
    private final Handler handler = new Handler();
    Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDifficultGameBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setViewTreeObserver();

        setTimer();
    }

    @Override
    public void onBackPressed() {
        difficultGameScreen.audio[0].stop();
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        difficultGameScreen.audio[0].pause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        difficultGameScreen.audio[0].stop();
    }

    private void setViewTreeObserver() {
        difficultGameScreen = binding.difficultGameScreen;

        ViewTreeObserver obs = difficultGameScreen.getViewTreeObserver();
        obs.addOnGlobalLayoutListener(() -> {
            difficultGameScreen.width = difficultGameScreen.getWidth();
            difficultGameScreen.height = difficultGameScreen.getHeight();
            difficultGameScreen.posX = difficultGameScreen.width / 2;
            difficultGameScreen.posY = difficultGameScreen.height - 100;
            difficultGameScreen.radio = 100;
            difficultGameScreen.beetY = 50;
            difficultGameScreen.beetX = random.nextInt(difficultGameScreen.width);
            difficultGameScreen.farmerX = random.nextInt(difficultGameScreen.width);
            difficultGameScreen.nephewX = random.nextInt(difficultGameScreen.width);
            difficultGameScreen.bearX = random.nextInt(difficultGameScreen.width);
            difficultGameScreen.goldenBeetX = random.nextInt(difficultGameScreen.width);
        });
    }

    private void setTimer() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(() -> {
                    if (difficultGameScreen.score < 0){
                        timer.cancel();
                        difficultGameScreen.audio[0].stop();
                        difficultGameScreen.audio[1].start();
                        goToGameOver();
                        difficultGameScreen.audio[2].start();
                    }
                    if (difficultGameScreen.score == 100){
                        timer.cancel();
                        difficultGameScreen.audio[0].stop();
                        goToVictory();
                    }

                    difficultGameScreen.beetY += 20;
                    difficultGameScreen.farmerY += 25;
                    difficultGameScreen.nephewY += 35;
                    difficultGameScreen.bearY += 30;
                    difficultGameScreen.goldenBeetY += 50;

                    difficultGameScreen.invalidate();
                });
            }
        }, 0, 30);
    }

    public void goToGameOver(){
        Intent intent = new Intent(this, DifficultGameOverActivity.class);
        startActivity(intent);
        finish();
    }

    public void goToVictory(){
        Intent intent = new Intent(this, VictoryActivity.class);
        startActivity(intent);
        finish();
    }
}
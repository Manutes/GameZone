package com.example.gamezone.ui.remolachagame.easygame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.gamezone.databinding.ActivityEasyGameBinding;
import com.example.gamezone.ui.remolachagame.gameover.EasyGameOverActivity;
import com.example.gamezone.ui.remolachagame.victory.VictoryActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import io.grpc.internal.LogExceptionRunnable;

public class EasyGameActivity extends AppCompatActivity {

    ActivityEasyGameBinding binding;

    public EasyGameScreen easyGameScreen;
    private final Handler handler = new Handler();

    ArrayList<Integer> scoreList = new ArrayList<>();

    Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        binding = ActivityEasyGameBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setViewTreeObserver();

        setTimer();
    }

    @Override
    public void onBackPressed() {
        easyGameScreen.audio[0].stop();
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        easyGameScreen.audio[0].pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        easyGameScreen.audio[0].start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        easyGameScreen.audio[0].stop();
    }

    private void setViewTreeObserver() {
        easyGameScreen = binding.easyGameScreen;

        ViewTreeObserver obs = easyGameScreen.getViewTreeObserver();
        obs.addOnGlobalLayoutListener(() -> {
            easyGameScreen.width = easyGameScreen.getWidth();
            easyGameScreen.height = easyGameScreen.getHeight();
            easyGameScreen.posX = easyGameScreen.width / 2;
            easyGameScreen.posY = easyGameScreen.height - 100;
            easyGameScreen.radio = 100;
            easyGameScreen.beetY = 50;
            easyGameScreen.beetX = random.nextInt(easyGameScreen.width);
            easyGameScreen.farmerX = random.nextInt(easyGameScreen.width);
            easyGameScreen.bearX = random.nextInt(easyGameScreen.width);
            easyGameScreen.goldenBeetX = random.nextInt(easyGameScreen.width);
        });
    }

    private void setTimer() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(() -> {
                    if (easyGameScreen.score < 0){
                        timer.cancel();
                        easyGameScreen.audio[0].stop();
                        easyGameScreen.audio[1].start();
                        goToGameOver();
                        easyGameScreen.audio[2].start();
                    }
                    if (easyGameScreen.score == 50){
                        timer.cancel();
                        easyGameScreen.audio[0].stop();
                        goToVictory();
                    }
                    scoreList.add(easyGameScreen.score);
                    scoreList.sort(Collections.reverseOrder());
                    scoreList.get(0).toString();

                    easyGameScreen.beetY += 10;
                    easyGameScreen.farmerY += 15;
                    easyGameScreen.bearY += 20;
                    easyGameScreen.goldenBeetY += 40;

                    easyGameScreen.invalidate();
                });
            }
        }, 0, 20);
    }

    public void goToGameOver(){
        Intent intent = new Intent(this, EasyGameOverActivity.class);
        startActivity(intent);
        finish();
    }

    public void goToVictory(){
        Intent intent = new Intent(this, VictoryActivity.class);
        startActivity(intent);
        finish();
    }
}
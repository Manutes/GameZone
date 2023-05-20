package com.example.gamezone.ui.remolachagame.easygame;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.example.gamezone.data.database.Firestore;
import com.example.gamezone.data.firebase.Firebase;
import com.example.gamezone.databinding.ActivityEasyGameBinding;
import com.example.gamezone.ui.remolachagame.gameover.EasyGameOverActivity;
import com.example.gamezone.ui.remolachagame.victory.VictoryActivity;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class EasyGameActivity extends AppCompatActivity {

    private final Handler handler = new Handler();
    public EasyGameScreen easyGameScreen;
    public ArrayList<Integer> scoreList = new ArrayList<>();
    ActivityEasyGameBinding binding;
    Firestore firestore = new Firestore();

    Firebase firebase = new Firebase();

    FirebaseUser user = Objects.requireNonNull(firebase.mFirebaseAuth.getCurrentUser());

    boolean isNewRecord = false;

    boolean musicIsActive = true;

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
        super.onBackPressed();
        if (musicIsActive) {
            easyGameScreen.audio[0].stop();
        }
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (musicIsActive) {
            easyGameScreen.audio[0].pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!musicIsActive) {
            musicIsActive = true;
            easyGameScreen.audio[0].start();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (musicIsActive) {
            easyGameScreen.audio[0].stop();
        }
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
                    if (easyGameScreen.score < 0) {
                        timer.cancel();
                        musicIsActive = false;
                        easyGameScreen.audio[0].stop();
                        easyGameScreen.audio[0].release();
                        easyGameScreen.audio[1].start();
                        firestore.updateScores(user, "RemolachaHeroLastScore", scoreList.get(0).toString());
                        checkNewRecord();
                        goToGameOver();
                    }
                    if (easyGameScreen.score == 50) {
                        timer.cancel();
                        easyGameScreen.audio[0].stop();
                        goToVictory();
                    }
                    scoreList.add(easyGameScreen.score);
                    scoreList.sort(Collections.reverseOrder());


                    easyGameScreen.beetY += 10;
                    easyGameScreen.farmerY += 15;
                    easyGameScreen.bearY += 20;
                    easyGameScreen.goldenBeetY += 40;

                    easyGameScreen.invalidate();
                });
            }
        }, 0, 20);
    }

    public void goToGameOver() {
        Intent intent = new Intent(this, EasyGameOverActivity.class);
        startActivity(intent);
        finish();
    }

    public void goToVictory() {
        Intent intent = new Intent(this, VictoryActivity.class);
        startActivity(intent);
        finish();
    }

    public MutableLiveData<String> getLastRecord() {
        MutableLiveData<String> recordLiveData = new MutableLiveData<>();
        Task<DocumentSnapshot> doc = firestore.getUserDocument(user.getUid());
        doc.addOnSuccessListener(documentSnapshot -> {
            String record = Objects.requireNonNull(documentSnapshot.getString("RemolachaHeroEasyRecord"));
            recordLiveData.postValue(record);

        });
        return recordLiveData;
    }

    private void checkNewRecord() {
        getLastRecord().observe(this, s -> {
            if (!Objects.equals(s, "0")) {
                isNewRecord = scoreList.get(0) > Integer.parseInt(s);
                if (isNewRecord) {
                    firestore.updateScores(user, "RemolachaHeroEasyRecord", scoreList.get(0).toString());
                }
            } else {
                firestore.updateScores(user, "RemolachaHeroEasyRecord", scoreList.get(0).toString());
            }

        });
    }
}
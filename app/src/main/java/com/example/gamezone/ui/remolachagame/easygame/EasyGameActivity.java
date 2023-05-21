package com.example.gamezone.ui.remolachagame.easygame;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.example.gamezone.R;
import com.example.gamezone.data.database.Firestore;
import com.example.gamezone.data.firebase.Firebase;
import com.example.gamezone.databinding.ActivityEasyGameBinding;
import com.example.gamezone.ui.remolachagame.gameover.EasyGameOverActivity;
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
    public ArrayList<Long> scoreList = new ArrayList<>();
    ActivityEasyGameBinding binding;
    Firestore firestore = new Firestore();

    Firebase firebase = new Firebase();

    FirebaseUser user = Objects.requireNonNull(firebase.mFirebaseAuth.getCurrentUser());

    boolean isNewRecord = false;

    int playPosition = 0;

    private MediaPlayer mp;

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
        stopMusic();
        finish();
    }

    private void setMediaPlayer() {
        if (mp != null) {
            mp.release();
        }
        mp = MediaPlayer.create(this, R.raw.easy);
        mp.start();
        mp.seekTo(playPosition);
        mp.setOnCompletionListener(MediaPlayer::start);
    }

    private void stopMusic() {
        if (mp != null) {
            mp.release();
            mp = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mp != null && mp.isPlaying()) {
            mp.pause();
            playPosition = mp.getCurrentPosition();
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
        stopMusic();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopMusic();
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
                        stopMusic();
                        firestore.updateScores(user, "RemolachaHeroLastScore", scoreList.get(0));
                        checkNewRecord();
                        goToGameOver();
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

    public MutableLiveData<Long> getLastRecord() {
        MutableLiveData<Long> recordLiveData = new MutableLiveData<>();
        Task<DocumentSnapshot> doc = firestore.getUserDocument(user.getUid());
        doc.addOnSuccessListener(documentSnapshot -> {
            Long record = Long.parseLong(Objects.requireNonNull(documentSnapshot.get("RemolachaHeroEasyRecord")).toString());
            recordLiveData.postValue(record);

        });
        return recordLiveData;
    }

    private void checkNewRecord() {
        getLastRecord().observe(this, s -> {
            if (!Objects.equals(s, 0L)) {
                isNewRecord = scoreList.get(0) > s;
                if (isNewRecord) {
                    firestore.updateScores(user, "RemolachaHeroEasyRecord", scoreList.get(0));
                }
            } else {
                firestore.updateScores(user, "RemolachaHeroEasyRecord", scoreList.get(0));
            }

        });
    }
}
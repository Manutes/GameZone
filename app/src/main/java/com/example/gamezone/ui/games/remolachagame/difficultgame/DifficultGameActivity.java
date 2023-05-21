package com.example.gamezone.ui.games.remolachagame.difficultgame;

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
import com.example.gamezone.databinding.ActivityDifficultGameBinding;
import com.example.gamezone.ui.games.remolachagame.gameover.DifficultGameOverActivity;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class DifficultGameActivity extends AppCompatActivity {

    ActivityDifficultGameBinding binding;

    public DifficultGameScreen difficultGameScreen;
    private final Handler handler = new Handler();
    public ArrayList<Long> scoreList = new ArrayList<>();

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
        binding = ActivityDifficultGameBinding.inflate(getLayoutInflater());
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
        mp = MediaPlayer.create(this, R.raw.dificil);
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
                        stopMusic();
                        firestore.updateScores(user, "RemolachaHeroLastScore", scoreList.get(0));
                        checkNewRecord();
                        goToGameOver();
                    }

                    scoreList.add(difficultGameScreen.score);
                    scoreList.sort(Collections.reverseOrder());

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

    public MutableLiveData<Long> getLastRecord() {
        MutableLiveData<Long> recordLiveData = new MutableLiveData<>();
        Task<DocumentSnapshot> doc = firestore.getUserDocument(user.getUid());
        doc.addOnSuccessListener(documentSnapshot -> {
            Long record =  Long.parseLong(Objects.requireNonNull(documentSnapshot.get("RemolachaHeroDifficultRecord")).toString());
            recordLiveData.postValue(record);

        });
        return recordLiveData;
    }

    private void checkNewRecord () {
        getLastRecord().observe(this, s -> {
            if (!Objects.equals(s, 0L)){
                isNewRecord = scoreList.get(0) > s;
                if (isNewRecord) {
                    firestore.updateScores(user, "RemolachaHeroDifficultRecord", scoreList.get(0));
                }
            } else {
                firestore.updateScores(user, "RemolachaHeroDifficultRecord", scoreList.get(0));
            }

        });
    }
}
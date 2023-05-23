package com.example.gamezone.ui.games.remolachagame.gameover;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.gamezone.R;
import com.example.gamezone.data.database.Firestore;
import com.example.gamezone.data.firebase.Firebase;
import com.example.gamezone.databinding.ActivityEasyGameOverBinding;
import com.example.gamezone.ui.games.remolachagame.easygame.EasyGameActivity;
import com.example.gamezone.ui.games.remolachagame.homescreen.HomeScreenActivity;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Objects;

public class EasyGameOverActivity extends AppCompatActivity {

    ActivityEasyGameOverBinding binding;

    Firestore db = new Firestore();

    Firebase firebase = new Firebase();

    int playPosition = 0;

    private MediaPlayer mp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        binding = ActivityEasyGameOverBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setBackgroundGif();
        setUi();

    }

    private void setUi() {
        binding.btnRestart.setOnClickListener(view -> restart());

        binding.btnHomeScreen.setOnClickListener(view -> goToHomeScreen());

        if (binding.getRoot().getWidth() <= 800) {
            binding.tvScore.setTextSize(getResources().getDimension(R.dimen.dimen_24));
            binding.btnRestart.setTextSize(getResources().getDimension(R.dimen.dimen_16));
            binding.btnRestart.setTextSize(getResources().getDimension(R.dimen.dimen_16));
        }

        setScore();
    }

    private void setBackgroundGif() {
        Glide.with(getBaseContext()).load(R.drawable.granjerobaile).into(binding.gif);
    }

    public void restart() {
        Intent intent = new Intent(this, EasyGameActivity.class);
        startActivity(intent);
        finish();
    }

    public void goToHomeScreen() {
        Intent intent = new Intent(this, HomeScreenActivity.class);
        startActivity(intent);
        finish();
    }

    private void setScore() {
        Task<DocumentSnapshot> doc = db.getUserDocument(Objects.requireNonNull(firebase.mFirebaseAuth.getCurrentUser()).getUid());
        doc.addOnSuccessListener(documentSnapshot -> {
            long score = Long.parseLong(Objects.requireNonNull(documentSnapshot.get("RemolachaHeroLastScore").toString()));
            binding.tvScore.setText("Conseguiste " + score + " remolacha/s");
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        stopMusic();
        goToHomeScreen();
    }

    private void setMediaPlayer() {
        if (mp != null) {
            mp.release();
        }
        mp = MediaPlayer.create(this, R.raw.yija);
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
}
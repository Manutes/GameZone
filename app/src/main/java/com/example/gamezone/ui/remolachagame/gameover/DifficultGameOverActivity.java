package com.example.gamezone.ui.remolachagame.gameover;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.gamezone.R;
import com.example.gamezone.data.database.Firestore;
import com.example.gamezone.data.firebase.Firebase;
import com.example.gamezone.databinding.ActivityDifficultGameOverBinding;
import com.example.gamezone.ui.remolachagame.difficultgame.DifficultGameActivity;
import com.example.gamezone.ui.remolachagame.difficultgame.DifficultGameScreen;
import com.example.gamezone.ui.remolachagame.homescreen.HomeScreenActivity;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Collections;

public class DifficultGameOverActivity extends AppCompatActivity {

    ActivityDifficultGameOverBinding binding;

    DifficultGameActivity game = new DifficultGameActivity();

    Firestore db = new Firestore();

    Firebase firebase = new Firebase();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        binding = ActivityDifficultGameOverBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setBackgroundGif();
        setUi();
    }

    private void setUi() {
        binding.btnRestart.setOnClickListener(view -> restart());

        binding.btnHomeScreen.setOnClickListener(view -> goToHomeScreen());

        setScore();
    }

    private void setBackgroundGif() {
        Glide.with(getBaseContext()).load(R.drawable.granjerobaile).into(binding.gif);
    }

    public void restart() {
        Intent intent = new Intent(this, DifficultGameActivity.class);
        startActivity(intent);
        finish();
    }

    public void goToHomeScreen() {
        Intent intent = new Intent(this, HomeScreenActivity.class);
        startActivity(intent);
        finish();
    }

    private void setScore() {
        Task<DocumentSnapshot> doc = db.getUserDocument(firebase.mFirebaseAuth.getCurrentUser().getUid());
        doc.addOnSuccessListener(documentSnapshot ->
                binding.tvScore.setText("Conseguiste " + documentSnapshot.getString("RemolachaHeroLastScore") + " remolacha/s"));
    }
}
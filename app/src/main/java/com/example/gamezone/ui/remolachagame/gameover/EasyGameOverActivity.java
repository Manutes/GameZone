package com.example.gamezone.ui.remolachagame.gameover;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.gamezone.R;
import com.example.gamezone.data.database.Firestore;
import com.example.gamezone.data.firebase.Firebase;
import com.example.gamezone.databinding.ActivityEasyGameOverBinding;
import com.example.gamezone.ui.remolachagame.easygame.EasyGameActivity;
import com.example.gamezone.ui.remolachagame.homescreen.HomeScreenActivity;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

public class EasyGameOverActivity extends AppCompatActivity {

    ActivityEasyGameOverBinding binding;

    EasyGameActivity game = new EasyGameActivity();

    Firestore db = new Firestore();

    Firebase firebase = new Firebase();

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
        Task<DocumentSnapshot> doc = db.getUserDocument(firebase.mFirebaseAuth.getCurrentUser().getUid());
        doc.addOnSuccessListener(documentSnapshot -> {
            Log.d("Score", documentSnapshot.getString("RemolachaHeroLastScore"));
            binding.tvScore.setText("Conseguiste " + documentSnapshot.getString("RemolachaHeroLastScore") + " remolacha/s");
        });
    }
}
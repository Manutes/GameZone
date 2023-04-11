package com.example.gamezone.remolachagame.gameover;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.gamezone.R;
import com.example.gamezone.databinding.ActivityEasyGameOverBinding;
import com.example.gamezone.remolachagame.easygame.EasyGameActivity;
import com.example.gamezone.remolachagame.homescreen.HomeScreenActivity;

public class EasyGameOverActivity extends AppCompatActivity {

    ActivityEasyGameOverBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEasyGameOverBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setBackgroundGif();
        setUi();
    }

    private void setUi() {
        binding.btnRestart.setOnClickListener(view -> restart());

        binding.btnHomeScreen.setOnClickListener(view -> goToHomeScreen());
    }

    private void setBackgroundGif() {
        Glide.with(getBaseContext()).load(R.drawable.granjerobaile).into(binding.gif);
    }

    public void restart() {
        Intent intent = new Intent(this, EasyGameActivity.class);
        startActivity(intent);
    }

    public void goToHomeScreen() {
        Intent intent = new Intent(this, HomeScreenActivity.class);
        startActivity(intent);
    }
}
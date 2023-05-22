package com.example.gamezone.ui;

import static com.example.gamezone.R.id;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.gamezone.databinding.ActivityMainBinding;
import com.example.gamezone.ui.home.HomeFragment;
import com.example.gamezone.ui.profile.ProfileFragment;
import com.example.gamezone.ui.ranking.RankingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding binding;

    FragmentManager fragmentManager = getSupportFragmentManager();

    final Fragment gamesFragment = new HomeFragment();
    final Fragment socialFragment = new RankingFragment();
    final Fragment optionsFragment = new ProfileFragment();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.navBar.setOnItemSelectedListener(this);
        binding.navBar.setSelectedItemId(id.games);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case id.games:
                fragmentManager.beginTransaction().replace(id.hostFragment, gamesFragment).commit();
                return true;

            case id.ranking:
                fragmentManager.beginTransaction().replace(id.hostFragment, socialFragment).commit();
                return true;

            case id.profile:
                fragmentManager.beginTransaction().replace(id.hostFragment, optionsFragment).commit();
                return true;
        }
        return false;
    }
}
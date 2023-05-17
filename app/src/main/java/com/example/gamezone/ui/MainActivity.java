package com.example.gamezone.ui;

import static com.example.gamezone.R.*;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.gamezone.databinding.ActivityMainBinding;
import com.example.gamezone.ui.games.GamesFragment;
import com.example.gamezone.options.OptionsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding binding;

    FragmentManager fragmentManager = getSupportFragmentManager();

    final Fragment gamesFragment = new GamesFragment();
    final Fragment socialFragment = new Fragment();
    final Fragment optionsFragment = new OptionsFragment();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

            case id.social:
                fragmentManager.beginTransaction().replace(id.hostFragment, socialFragment).commit();
                return true;

            case id.options:
                fragmentManager.beginTransaction().replace(id.hostFragment, optionsFragment).commit();
                return true;
        }
        return false;
    }
}
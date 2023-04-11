package com.example.gamezone;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.gamezone.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setupBottomNav();
    }

    private void setupBottomNav() {

        FragmentManager fragmentManager = getSupportFragmentManager();

        final Fragment gamesFragment = new GamesFragment();
        final Fragment socialFragment = new Fragment();
        final Fragment optionsFragment = new Fragment();

        fragmentManager.beginTransaction()
                .add(R.id.hostFragment, gamesFragment, GamesFragment.class.getName()).commit();

        fragmentManager.beginTransaction()
                .add(R.id.hostFragment, socialFragment).hide(socialFragment).commit();

        fragmentManager.beginTransaction()
                .add(R.id.hostFragment, optionsFragment).hide(optionsFragment).commit();

        binding.navBar.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.games:
                    fragmentManager.beginTransaction().replace(R.id.hostFragment, gamesFragment).commit();
                    return true;

                case R.id.social:
                    fragmentManager.beginTransaction().replace(R.id.hostFragment, socialFragment).commit();
                    return true;

                case R.id.options:
                    fragmentManager.beginTransaction().replace(R.id.hostFragment, optionsFragment).commit();
                    return true;
            }
            return false;
        });
    }

}
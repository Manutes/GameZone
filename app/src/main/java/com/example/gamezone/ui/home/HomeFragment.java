package com.example.gamezone.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.gamezone.data.models.Game;
import com.example.gamezone.databinding.FragmentHomeBinding;
import com.example.gamezone.ui.games.clickergame.ClickerGame;
import com.example.gamezone.ui.games.marcianosGame.MarcianosMainActivity;
import com.example.gamezone.ui.games.remolachagame.homescreen.HomeScreenActivity;
import com.example.gamezone.ui.home.adapter.HomeAdapter;
import com.example.gamezone.ui.home.adapter.SetOnClickListener;

import java.util.List;

public class HomeFragment extends Fragment implements SetOnClickListener {

    private List<Game> games;
    private FragmentHomeBinding binding;

    private Context context;

    private HomeViewModel homeViewModel;

    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContext();
        setViewModel();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        setAdapter();
        return binding.getRoot();
    }

    private void setViewModel() {
        homeViewModel = new HomeViewModel();
        games = homeViewModel.setGamesList(context);
    }

    private void setContext() {
        context = requireContext();
    }

    private void setAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        HomeAdapter homeAdapter = new HomeAdapter(context, games, this);
        binding.recyclerView.setAdapter(homeAdapter);
    }

    @Override
    public void onItemClick(Game game, int position) {
        Toast.makeText(context, game.getTitle(), Toast.LENGTH_SHORT).show();
        switch (game.getId()) {
            case 1:
                Intent intent = new Intent(context, HomeScreenActivity.class);
                startActivity(intent);
                break;
            case 2:
                Intent intentCG = new Intent(context, ClickerGame.class);
                startActivity(intentCG);
                break;
            case 3:
                Intent intentM = new Intent(context, MarcianosMainActivity.class);
                startActivity(intentM);
                break;

        }

    }

}
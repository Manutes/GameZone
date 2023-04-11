package com.example.gamezone.games;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gamezone.SetOnClickListener;
import com.example.gamezone.databinding.FragmentGamesBinding;
import com.example.gamezone.games.adapter.GamesAdapter;
import com.example.gamezone.games.models.Game;

import java.util.List;

public class GamesFragment extends Fragment implements SetOnClickListener {

    private List<Game> games;
    private FragmentGamesBinding binding;

    private Context context;

    private GamesViewModel gamesViewModel;

    public GamesFragment() {
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
        binding = FragmentGamesBinding.inflate(inflater, container, false);
        setAdapter();
        return binding.getRoot();
    }

    private void setViewModel() {
        gamesViewModel = new GamesViewModel();
        games = gamesViewModel.setGamesList(context);
    }

    private void setContext() {
        context = requireContext();
    }

    private void setAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        GamesAdapter gamesAdapter = new GamesAdapter(context, games, this);
        binding.recyclerView.setAdapter(gamesAdapter);
    }

    @Override
    public void onItemClick(Game game, int position) {
        Toast.makeText(context, "Remolacha Hero Seleccionado", Toast.LENGTH_LONG).show();
    }

}
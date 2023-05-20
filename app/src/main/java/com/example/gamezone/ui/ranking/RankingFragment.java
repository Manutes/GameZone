package com.example.gamezone.ui.ranking;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.gamezone.R;
import com.example.gamezone.data.models.GameRanking;
import com.example.gamezone.databinding.FragmentRankingBinding;
import com.example.gamezone.ui.ranking.adapter.RankingAdapter;

import java.util.ArrayList;


public class RankingFragment extends Fragment {

    FragmentRankingBinding binding;

    private ArrayList<GameRanking> gamesRankingList = new ArrayList<>();

    private RankingViewModel rankingViewModel;

    private Context context;

    ArrayList<Long> rankingRemolachaHeroEasy = new ArrayList<>();
    ArrayList<Long> rankingRemolachaHeroDifficult = new ArrayList<>();
    ArrayList<Long> rankingClickerGame = new ArrayList<>();
    ArrayList<Long> rankingMarcianitos = new ArrayList<>();

    public RankingFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContext();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRankingBinding.inflate(inflater, null, false);
        setViewModel();
        return binding.getRoot();
    }


    private void setViewModel() {
        rankingViewModel = new RankingViewModel();
        setObservers();
    }

    private void setContext() {
        context = requireContext();
    }

    private void setAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        binding.rankingRv.setLayoutManager(linearLayoutManager);
        RankingAdapter gamesRankingAdapter = new RankingAdapter(gamesRankingList);
        binding.rankingRv.setAdapter(gamesRankingAdapter);
    }

    private void setObservers() {
        ArrayList<GameRanking> gamesRanking = new ArrayList<>();

        rankingViewModel.getRemolachaEasyRanking().observe(getViewLifecycleOwner(), list -> {
            rankingRemolachaHeroEasy = list;
            GameRanking remolachaEasyGame = new GameRanking(context.getString(R.string.ranking_remolacha_hero_easy),
                    "sdfsf", rankingRemolachaHeroEasy.get(0).toString(),
                    "dewrwere", rankingRemolachaHeroEasy.get(1).toString(),
                    "skuyt", rankingRemolachaHeroEasy.get(2).toString());
            gamesRanking.add(remolachaEasyGame);
        });
        rankingViewModel.getRemolachaDifficultRanking().observe(getViewLifecycleOwner(), list -> {
            rankingRemolachaHeroDifficult = list;
            GameRanking remolachaDifficultGame = new GameRanking(context.getString(R.string.ranking_remolacha_hero_difficult),
                    "rter", rankingRemolachaHeroDifficult.get(0).toString(),
                    "tehf", rankingRemolachaHeroDifficult.get(1).toString(),
                    "juyki", rankingRemolachaHeroDifficult.get(2).toString());
            gamesRanking.add(remolachaDifficultGame);
        });
        rankingViewModel.getClickerGameRanking().observe(getViewLifecycleOwner(), list -> {
            rankingClickerGame = list;
            GameRanking clickerGame = new GameRanking(context.getString(R.string.ranking_clicker_game),
                    "rtree", rankingClickerGame.get(0).toString(),
                    "ewww", rankingClickerGame.get(1).toString(),
                    "bcff", rankingClickerGame.get(2).toString());
            gamesRanking.add(clickerGame);
        });
        rankingViewModel.getMarcianitosRanking().observe(getViewLifecycleOwner(), list -> {
            rankingMarcianitos = list;
            GameRanking marcianitosGame = new GameRanking(context.getString(R.string.ranking_marcianitos),
                    "trreeertdg", rankingMarcianitos.get(0).toString(),
                    "hgf", rankingMarcianitos.get(1).toString(),
                    "jgg", rankingMarcianitos.get(2).toString());
            gamesRanking.add(marcianitosGame);
            gamesRankingList = gamesRanking;
            setAdapter();
        });


    }
}
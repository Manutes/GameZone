package com.example.gamezone.ui.ranking;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gamezone.R;
import com.example.gamezone.data.models.GameRanking;
import com.example.gamezone.databinding.FragmentOptionsBinding;
import com.example.gamezone.databinding.FragmentRankingBinding;
import com.example.gamezone.ui.games.GamesViewModel;
import com.example.gamezone.ui.games.adapter.GamesAdapter;
import com.example.gamezone.ui.ranking.adapter.RankingAdapter;

import java.util.List;


public class RankingFragment extends Fragment {

    FragmentRankingBinding binding;

    private List<GameRanking> gamesRanking;

    private RankingViewModel rankingViewModel;

    private Context context;

    public RankingFragment() {
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
        binding = FragmentRankingBinding.inflate(inflater, null, false);
        setAdapter();
        return binding.getRoot();
    }


    private void setViewModel() {
        rankingViewModel = new RankingViewModel();
        gamesRanking = rankingViewModel.setGamesRankingList(context);
    }

    private void setContext() { context = requireContext(); }

    private void setAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        binding.rankingRv.setLayoutManager(linearLayoutManager);
        RankingAdapter gamesRankingAdapter = new RankingAdapter(gamesRanking);
        binding.rankingRv.setAdapter(gamesRankingAdapter);
    }

}
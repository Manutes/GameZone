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
import java.util.HashMap;
import java.util.Map;


public class RankingFragment extends Fragment {

    FragmentRankingBinding binding;

    private ArrayList<GameRanking> gamesRankingList = new ArrayList<>();

    private RankingViewModel rankingViewModel;

    private Context context;

    ArrayList<Map<String, Long>> ranking = new ArrayList<>();
    ArrayList<String> userRanking = new ArrayList<>();
    ArrayList<Long> pointsRanking = new ArrayList<>();

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
            ranking = list;
            for (int i = 0; i < ranking.size(); i++) {
                getTopRanking(userRanking, pointsRanking, ranking, i);
            }
            GameRanking remolachaEasyGame = new GameRanking(context.getString(R.string.ranking_remolacha_hero_easy),
                    userRanking.get(0), pointsRanking.get(0).toString(),
                    userRanking.get(1), pointsRanking.get(1).toString(),
                    userRanking.get(2), pointsRanking.get(2).toString());
            gamesRanking.add(remolachaEasyGame);
            clear();
        });



        rankingViewModel.getRemolachaDifficultRanking().observe(getViewLifecycleOwner(), list -> {
            ranking = list;
            for (int i = 0; i < ranking.size(); i++) {
                getTopRanking(userRanking, pointsRanking, ranking, i);
            }
            GameRanking remolachaDifficultGame = new GameRanking(context.getString(R.string.ranking_remolacha_hero_difficult),
                    userRanking.get(0), pointsRanking.get(0).toString(),
                    userRanking.get(1), pointsRanking.get(1).toString(),
                    userRanking.get(2), pointsRanking.get(2).toString());
            gamesRanking.add(remolachaDifficultGame);
            clear();
        });

        rankingViewModel.getClickerGameRanking().observe(getViewLifecycleOwner(), list -> {
            ranking = list;
            for (int i = 0; i < ranking.size(); i++) {
                getTopRanking(userRanking, pointsRanking, ranking, i);
            }
            GameRanking clickerGame = new GameRanking(context.getString(R.string.ranking_clicker_game),
                    userRanking.get(0), pointsRanking.get(0).toString(),
                    userRanking.get(1), pointsRanking.get(1).toString(),
                    userRanking.get(2), pointsRanking.get(2).toString());
            gamesRanking.add(clickerGame);
            clear();
        });
        rankingViewModel.getMarcianitosRanking().observe(getViewLifecycleOwner(), list -> {
            ranking = list;
            for (int i = 0; i < ranking.size(); i++) {
                getTopRanking(userRanking, pointsRanking, ranking, i);
            }
            GameRanking marcianitosGame = new GameRanking(context.getString(R.string.ranking_marcianitos),
                    userRanking.get(0), pointsRanking.get(0).toString(),
                    userRanking.get(1), pointsRanking.get(1).toString(),
                    userRanking.get(2), pointsRanking.get(2).toString());
            gamesRanking.add(marcianitosGame);
            gamesRankingList = gamesRanking;
            clear();
            setAdapter();
        });
    }

    private void getTopRanking(ArrayList<String> user, ArrayList<Long> points, ArrayList<Map<String, Long>> ranking, int index) {
        Map<String, Long> map = new HashMap<>(ranking.get(index));
        map.forEach((s, aLong) -> {
            user.add(s);
            points.add(aLong);
        });
    }

    private void clear() {
        userRanking.clear();
        pointsRanking.clear();
        ranking.clear();
    }
}
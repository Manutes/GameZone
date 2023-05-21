package com.example.gamezone.ui.ranking;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;


public class RankingFragment extends Fragment {

    FragmentRankingBinding binding;

    private ArrayList<GameRanking> gamesRankingList = new ArrayList<>();

    private RankingViewModel rankingViewModel;

    private Context context;

    ArrayList<Map<String, Long>> rankingRemolachaHeroEasy = new ArrayList<>();
    ArrayList<Map<String, Long>> rankingRemolachaHeroDifficult = new ArrayList<>();
    ArrayList<Map<String, Long>> rankingClickerGame = new ArrayList<>();
    ArrayList<Map<String, Long>> rankingMarcianitos = new ArrayList<>();

    ArrayList<String> userRemolachaEasy = new ArrayList<>();
    ArrayList<Long> pointsRemolachaEasy = new ArrayList<>();
    ArrayList<String> userRemolachaDifficult = new ArrayList<>();
    ArrayList<Long> pointsRemolachaDifficult = new ArrayList<>();
    ArrayList<String> userClickerGame = new ArrayList<>();
    ArrayList<Long> pointsClickerGame = new ArrayList<>();
    ArrayList<String> userMarcianitos = new ArrayList<>();
    ArrayList<Long> pointsMarcianitos = new ArrayList<>();

    ArrayList<String> user1 = new ArrayList<>();

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
            Log.d("Easy", list.toString());
            getTopRanking(userRemolachaEasy, pointsRemolachaEasy, rankingRemolachaHeroEasy, 0);
            getTopRanking(userRemolachaEasy, pointsRemolachaEasy, rankingRemolachaHeroEasy, 1);
            getTopRanking(userRemolachaEasy, pointsRemolachaEasy, rankingRemolachaHeroEasy, 2);

            GameRanking remolachaEasyGame = new GameRanking(context.getString(R.string.ranking_remolacha_hero_easy),
                    userRemolachaEasy.get(0), pointsRemolachaEasy.get(0).toString(),
                    userRemolachaEasy.get(1), pointsRemolachaEasy.get(1).toString(),
                    userRemolachaEasy.get(2), pointsRemolachaEasy.get(2).toString());
            gamesRanking.add(remolachaEasyGame);

        });

        rankingViewModel.getRemolachaDifficultRanking().observe(getViewLifecycleOwner(), list -> {
            rankingRemolachaHeroDifficult = list;
            Log.d("Difficult", list.toString());
            getTopRanking(userRemolachaDifficult, pointsRemolachaDifficult, rankingRemolachaHeroDifficult, 0);
            getTopRanking(userRemolachaDifficult, pointsRemolachaDifficult, rankingRemolachaHeroDifficult, 1);
            getTopRanking(userRemolachaDifficult, pointsRemolachaDifficult, rankingRemolachaHeroDifficult, 2);
            Collections.sort(pointsRemolachaDifficult);
            Log.d("DifficultSorted", pointsRemolachaDifficult.toString());
            GameRanking remolachaDifficultGame = new GameRanking(context.getString(R.string.ranking_remolacha_hero_difficult),
                    userRemolachaDifficult.get(0), pointsRemolachaDifficult.get(0).toString(),
                    userRemolachaDifficult.get(1), pointsRemolachaDifficult.get(1).toString(),
                    userRemolachaDifficult.get(2), pointsRemolachaDifficult.get(2).toString());
            gamesRanking.add(remolachaDifficultGame);
        });

        rankingViewModel.getClickerGameRanking().observe(getViewLifecycleOwner(), list -> {
            rankingClickerGame = list;
            Log.d("Clicker", list.toString());

            for (int i = 0; i < rankingRemolachaHeroDifficult.size(); i++) {
                getTopRanking(userClickerGame, pointsClickerGame, rankingClickerGame, i);
            }
            pointsClickerGame.sort(Collections.reverseOrder());
            Log.d("Points sorted", pointsClickerGame.toString());
            GameRanking clickerGame = new GameRanking(context.getString(R.string.ranking_clicker_game),
                    userClickerGame.get(0), pointsClickerGame.get(0).toString(),
                    userClickerGame.get(1), pointsClickerGame.get(1).toString(),
                    userClickerGame.get(2), pointsClickerGame.get(2).toString());
            gamesRanking.add(clickerGame);
        });

        rankingViewModel.getMarcianitosRanking().observe(getViewLifecycleOwner(), list -> {
            rankingMarcianitos = list;
            Log.d("Marcianitos", list.toString());
            getTopRanking(userMarcianitos, pointsMarcianitos, rankingMarcianitos, 0);
            getTopRanking(userMarcianitos, pointsMarcianitos, rankingMarcianitos, 1);
            getTopRanking(userMarcianitos, pointsMarcianitos, rankingMarcianitos, 2);
            GameRanking marcianitosGame = new GameRanking(context.getString(R.string.ranking_marcianitos),
                    userMarcianitos.get(0), pointsMarcianitos.get(0).toString(),
                    userMarcianitos.get(1), pointsMarcianitos.get(1).toString(),
                    userMarcianitos.get(2), pointsMarcianitos.get(2).toString());
            gamesRanking.add(marcianitosGame);
            gamesRankingList = gamesRanking;
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

}
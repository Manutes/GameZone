package com.example.gamezone.ui.ranking;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.example.gamezone.R;
import com.example.gamezone.data.models.GameRanking;

import java.util.ArrayList;
import java.util.List;

public class RankingViewModel extends ViewModel {

    public List<GameRanking> setGamesRankingList(Context context) {

        List<GameRanking> gamesRanking = new ArrayList<>();
        GameRanking remolachaEasyGame = new GameRanking(context.getString(R.string.ranking_remolacha_hero_easy),
                "sdfsf", "12213",
                "dewrwere", "2334",
                "skuyt", "32543");
        gamesRanking.add(remolachaEasyGame);

        GameRanking remolachaDifficultGame = new GameRanking(context.getString(R.string.ranking_remolacha_hero_difficult),
                "rter", "7656445",
                "tehf", "5345343",
                "juyki", "345322354");
        gamesRanking.add(remolachaDifficultGame);

        GameRanking clickerGame = new GameRanking(context.getString(R.string.ranking_clicker_game),
                "rtree", "344",
                "ewww", "566",
                "bcff", "8765");
        gamesRanking.add(clickerGame);

        GameRanking marcianitosGame = new GameRanking(context.getString(R.string.ranking_marcianitos),
                "trreeertdg", "76",
                "hgf", "65",
                "jgg", "34");
        gamesRanking.add(clickerGame);
        return gamesRanking;
    }
}

package com.example.gamezone.ui.home;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.example.gamezone.R;
import com.example.gamezone.data.models.Game;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private List<Game> games;

    public List<Game> setGamesList(Context context) {

        games = new ArrayList<>();
        Game remolachaGame = new Game(1, context.getString(R.string.titulo_juego_remolacha), R.drawable.remolacha_banner);
        games.add(remolachaGame);

        Game clickerGame = new Game(2, context.getString(R.string.titulo_juego_clickergame), R.drawable.clickergame_banner);
        games.add(clickerGame);
        return games;
    }
}

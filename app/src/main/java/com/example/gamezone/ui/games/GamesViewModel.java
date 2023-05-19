package com.example.gamezone.ui.games;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.example.gamezone.R;
import com.example.gamezone.data.models.Game;

import java.util.ArrayList;
import java.util.List;

public class GamesViewModel extends ViewModel {

    private List<Game> games;

    public List<Game> setGamesList(Context context) {

        games = new ArrayList<>();
        Game remolachaGame = new Game(1, context.getString(R.string.titulo_juego_remolacha), R.drawable.remolacha_img);
        games.add(remolachaGame);

        Game clickerGame = new Game(2, context.getString(R.string.titulo_juego_clickergame), R.drawable.remolacha_img);
        games.add(clickerGame);
        return games;
    }
}

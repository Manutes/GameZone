package com.example.gamezone.ui.games.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gamezone.databinding.ItemJuegoBinding;
import com.example.gamezone.data.models.Game;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.GameViewHolder> {

    private final List<Game> games;
    private final SetOnClickListener setOnClickListener;

    private final Context context;
    ItemJuegoBinding binding;

    public static class GameViewHolder extends RecyclerView.ViewHolder {
        ItemJuegoBinding binding;

        public GameViewHolder(ItemJuegoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public GamesAdapter(Context context, List<Game> games, SetOnClickListener setOnClickListener) {
        this.context = context;
        this.games = games;
        this.setOnClickListener = setOnClickListener;

    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemJuegoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new GameViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(GameViewHolder holder, int position) {
        Game game = games.get(position);
        holder.binding.tvTitleGame.setText(game.getTitle());
        holder.binding.buttonGame.setImageDrawable(AppCompatResources.getDrawable(context, game.getImage()));
        setButtonGame(holder.binding.buttonGame);
        holder.binding.buttonGame.setOnClickListener(view -> {
            setOnClickListener.onItemClick(game, position);
        });
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    private void setButtonGame(ShapeableImageView buttonGame) {
        buttonGame.setShapeAppearanceModel(buttonGame.getShapeAppearanceModel()
                .toBuilder()
                .setTopRightCornerSize(15f)
                .setBottomLeftCornerSize(15f)
                .setBottomRightCornerSize(120f)
                .setTopLeftCornerSize(120f)
                .build());
    }
}

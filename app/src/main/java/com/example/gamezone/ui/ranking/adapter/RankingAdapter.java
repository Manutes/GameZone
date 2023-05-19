package com.example.gamezone.ui.ranking.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gamezone.data.models.GameRanking;
import com.example.gamezone.databinding.ItemRankingBinding;

import java.util.List;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.RankingViewHolder>{

    private final List<GameRanking> gameRankings;
    ItemRankingBinding binding;

    public static class RankingViewHolder extends RecyclerView.ViewHolder {
        ItemRankingBinding binding;

        public RankingViewHolder(ItemRankingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public RankingAdapter(List<GameRanking> gameRankings) {
        this.gameRankings = gameRankings;
    }

    @NonNull
    @Override
    public RankingAdapter.RankingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemRankingBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new RankingAdapter.RankingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RankingAdapter.RankingViewHolder holder, int position) {
        GameRanking gameRanking = gameRankings.get(position);

        holder.binding.rankingTitle.setText(gameRanking.getTitle());
        holder.binding.goldUsername.setText(gameRanking.getGoldUsername());
        holder.binding.goldPoints.setText(gameRanking.getGoldPoints());
        holder.binding.silverUsername.setText(gameRanking.getSilverUsername());
        holder.binding.silverPoints.setText(gameRanking.getSilverPoints());
        holder.binding.bronzeUsername.setText(gameRanking.getBronzeUsername());
        holder.binding.bronzePoints.setText(gameRanking.getBronzePoints());
    }

    @Override
    public int getItemCount() {
        return gameRankings.size();
    }

}

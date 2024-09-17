package dev.jay.ultimatepokedex.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import dev.jay.ultimatepokedex.R;

public class TrainerAdapter extends RecyclerView.Adapter<TrainerAdapter.ViewHolder> {

    public List<String> images = new ArrayList<>();

    public TrainerAdapter(List<String> images) {
        this.images = images;
    }

    @NonNull
    @Override
    public TrainerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trainer, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TrainerAdapter.ViewHolder holder, int position) {
        holder.bind(images.get(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivAvatar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAvatar = itemView.findViewById(R.id.ivAvatar);
        }

        public void bind(String url) {
            Glide.with(itemView.getContext()).load(url).into(ivAvatar);
        }
    }
}

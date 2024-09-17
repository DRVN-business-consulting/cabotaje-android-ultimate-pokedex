package dev.jay.ultimatepokedex.adapters;

import static dev.jay.ultimatepokedex.api.API.BASE_URL;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import dev.jay.ultimatepokedex.ManagePokemonActivity;
import dev.jay.ultimatepokedex.PokemonDetailsActivity;
import dev.jay.ultimatepokedex.R;
import dev.jay.ultimatepokedex.api.API;
import dev.jay.ultimatepokedex.model.dto.response.SuccessDto;
import dev.jay.ultimatepokedex.model.dto.response.UserDto;
import dev.jay.ultimatepokedex.model.dto.response.pokemon.Image;
import dev.jay.ultimatepokedex.model.dto.response.pokemon.Pokemon;
import dev.jay.ultimatepokedex.prefs.AppPreferences;
import dev.jay.ultimatepokedex.secure_local_db.dao.FavoritePokemonDao;
import dev.jay.ultimatepokedex.secure_local_db.entity.dto.FavoritePokemonDTO;
import dev.jay.ultimatepokedex.utils.PokemonTypes;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoritePokemonAdapter extends RecyclerView.Adapter<FavoritePokemonAdapter.PokedexViewHolder> {

    private Context context;
    private String baseUrl;
    private List<FavoritePokemonDTO> pokemons;

    public FavoritePokemonAdapter(Context context, List<FavoritePokemonDTO> pokemons) {
        this.context = context;
        this.pokemons = pokemons;
        this.baseUrl = BASE_URL.substring(0, BASE_URL.length() - 1);
    }

    @NonNull
    @Override
    public FavoritePokemonAdapter.PokedexViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavoritePokemonAdapter.PokedexViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritePokemonAdapter.PokedexViewHolder holder, int position) {
        holder.bind(pokemons.get(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return pokemons.size();
    }

    class PokedexViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivThumb;
        private final TextView tvName;
        private final MaterialButton btnFavorite;

        public PokedexViewHolder(@NonNull View itemView) {
            super(itemView);
            ivThumb = itemView.findViewById(R.id.ivThumbnail);
            tvName = itemView.findViewById(R.id.tvName);

            btnFavorite = itemView.findViewById(R.id.btnFavorite);
        }

        public void bind(FavoritePokemonDTO pokemon) {
            //Log.d("bind", baseUrl + pokemon.getImage().getThumbnail());

            Glide.with(context).load(baseUrl + pokemon.getThumbURL()).into(ivThumb);
            tvName.setText(pokemon.getName());

            boolean isFavorite = FavoritePokemonDao.isFavorite(pokemon.getId(), pokemon.getUsername());
            btnFavorite.setIconResource(isFavorite? R.drawable.ic_favorite : R.drawable.ic_favorite_border);
            btnFavorite.setOnClickListener(fav -> {
                if(isFavorite) {
                    FavoritePokemonDao.removeToFavorite(pokemon.getId(), pokemon.getUsername());
                    pokemons.remove(pokemon);
                }
                notifyItemRemoved(getAdapterPosition());
            });
//
//            int[]typeIcons = pokemon.getTypeIcons();
//            ivTypeOne.setImageResource(typeIcons[0]);
//            if(typeIcons.length > 1){
//                ivTypeTwo.setVisibility(View.VISIBLE);
//                ivTypeTwo.setImageResource(typeIcons[1]);
//            } else {
//                ivTypeTwo.setVisibility(View.GONE);
//            }
            itemView.setOnClickListener(view -> {
                Intent intent = new Intent(context, PokemonDetailsActivity.class);
                intent.putExtra("id", pokemon.getId());
                context.startActivity(intent);
            });
        }
    }
}


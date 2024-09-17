package dev.jay.ultimatepokedex.adapters;

import static dev.jay.ultimatepokedex.api.API.BASE_URL;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
import dev.jay.ultimatepokedex.secure_local_db.dao.UserDataDao;
import dev.jay.ultimatepokedex.utils.PokemonTypes;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PokedexAdapter extends RecyclerView.Adapter<PokedexAdapter.PokedexViewHolder> {

    private Context context;
    private String baseUrl;
    private List<Pokemon> pokemons;
    private PokemonTypes pokemonTypes;
    private String username;

    public PokedexAdapter(Context context, List<Pokemon> pokemons) {
        this.context = context;
        this.pokemons = pokemons;
        this.baseUrl = BASE_URL.substring(0, BASE_URL.length() - 1);
        this.pokemonTypes = new PokemonTypes();
        username = UserDataDao.getLastUserName();
    }

    @NonNull
    @Override
    public PokedexViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PokedexViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PokedexViewHolder holder, int position) {
        holder.bind(pokemons.get(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return pokemons.size();
    }

    class PokedexViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivThumb, ivTypeOne, ivTypeTwo;
        private final TextView tvName;
        private final Button btnEdit, btnDelete;
        private final MaterialButton btnFavorite;

        public PokedexViewHolder(@NonNull View itemView) {
            super(itemView);
            ivThumb = itemView.findViewById(R.id.ivThumbnail);
            tvName = itemView.findViewById(R.id.tvName);

            ivTypeOne = itemView.findViewById(R.id.ivTypeOne);
            ivTypeTwo = itemView.findViewById(R.id.ivTypeTwo);

            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnFavorite = itemView.findViewById(R.id.btnFavorite);
        }

        public void bind(Pokemon pokemon) {
            //Log.d("bind", baseUrl + pokemon.getImage().getThumbnail());

            Image image = pokemon.getImage();
            Glide.with(context).load(image != null? baseUrl + pokemon.getImage().getThumbnail() : "https://placehold.co/150").into(ivThumb);
            tvName.setText(pokemon.getName().getEnglish());


            ArrayList<String> types = pokemon.getType();
            ivTypeOne.setImageResource(pokemonTypes.getTypeIcon(types.get(0)));
            if(types.size() > 1){
                ivTypeTwo.setVisibility(View.VISIBLE);
                ivTypeTwo.setImageResource(pokemonTypes.getTypeIcon(types.get(1)));
            } else {
                ivTypeTwo.setVisibility(View.GONE);
            }

            btnEdit.setOnClickListener(editView -> {
                Intent intent = new Intent(context, ManagePokemonActivity.class);
                intent.putExtra("id", pokemon.getId());
                context.startActivity(intent);
            });


            btnDelete.setOnClickListener(deleteView -> {
                API.pokemonApi().deletePokemonById(pokemon.getId()).enqueue(new Callback<SuccessDto>() {
                    @Override
                    public void onResponse(Call<SuccessDto> call, Response<SuccessDto> response) {
                        if(response.isSuccessful()) {
                            SuccessDto successDto = response.body();
                            Toast.makeText(context, successDto.getMessage(), Toast.LENGTH_SHORT).show();
                            pokemons.remove(pokemon);
                            notifyItemRemoved(getAdapterPosition());
                        }
                    }

                    @Override
                    public void onFailure(Call<SuccessDto> call, Throwable t) {

                    }
                });

            });

            boolean isFavorite = FavoritePokemonDao.isFavorite(pokemon.getId(), username);
            btnFavorite.setIconResource(isFavorite? R.drawable.ic_favorite : R.drawable.ic_favorite_border);
            btnFavorite.setOnClickListener(fav -> {
                if(isFavorite) {
                    FavoritePokemonDao.removeToFavorite(pokemon.getId(), username);
                } else {
                    FavoritePokemonDao.addToFavorite(pokemon.getId(), pokemon.getName().getEnglish(), pokemon.getImage() != null? pokemon.getImage().getThumbnail() : null, username);
                }
                notifyItemChanged(getAdapterPosition());
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

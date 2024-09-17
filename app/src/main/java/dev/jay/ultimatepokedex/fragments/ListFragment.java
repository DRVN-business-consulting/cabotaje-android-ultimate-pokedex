package dev.jay.ultimatepokedex.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import dev.jay.ultimatepokedex.ManagePokemonActivity;
import dev.jay.ultimatepokedex.R;
import dev.jay.ultimatepokedex.adapters.PokedexAdapter;
import dev.jay.ultimatepokedex.api.API;
import dev.jay.ultimatepokedex.model.dto.response.pokemon.Pokemon;
import dev.jay.ultimatepokedex.secure_local_db.dao.PokemonLocationDao;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListFragment extends Fragment {

    private PokedexAdapter pokedexAdapter;
    private RecyclerView rvPokemons;

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        rvPokemons = view.findViewById(R.id.rvPokemons);

        view.findViewById(R.id.btnAdd).setOnClickListener(btnView -> {
            startActivity(new Intent(getActivity(), ManagePokemonActivity.class));
        });


        return  view;
    }

    @Override
    public void onStart() {
        super.onStart();
        API.pokemonApi().getPokemons().enqueue(new Callback<List<Pokemon>>() {
            @Override
            public void onResponse(Call<List<Pokemon>> call, Response<List<Pokemon>> response) {
                if (response.isSuccessful()) {
                    List<Pokemon> pokemons = response.body();
                    if (pokemons != null) {
                        for (Pokemon pokemon : pokemons) {
                            if(pokemon.getId() != null) {
                                PokemonLocationDao.insertLocation(pokemon.getId());
                            }
                        }
                        pokedexAdapter = new PokedexAdapter(getActivity(), pokemons);

                        rvPokemons.setLayoutManager(new LinearLayoutManager(getActivity()));
                        rvPokemons.setAdapter(pokedexAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Pokemon>> call, Throwable t) {

            }
        });
    }
}
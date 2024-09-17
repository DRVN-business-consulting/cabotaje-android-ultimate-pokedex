package dev.jay.ultimatepokedex.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import dev.jay.ultimatepokedex.R;
import dev.jay.ultimatepokedex.adapters.FavoritePokemonAdapter;
import dev.jay.ultimatepokedex.adapters.PokedexAdapter;
import dev.jay.ultimatepokedex.model.dto.response.UserDto;
import dev.jay.ultimatepokedex.prefs.AppPreferences;
import dev.jay.ultimatepokedex.secure_local_db.dao.FavoritePokemonDao;
import dev.jay.ultimatepokedex.secure_local_db.dao.UserDataDao;
import dev.jay.ultimatepokedex.secure_local_db.entity.dto.FavoritePokemonDTO;

public class FavouritesFragment extends Fragment {

    private FavoritePokemonAdapter favoritePokemonAdapter;

    private RecyclerView rvPokemons;

    public FavouritesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        AppPreferences.initialize(getActivity());

        View view = inflater.inflate(R.layout.fragment_favourites, container, false);
        rvPokemons = view.findViewById(R.id.rvPokemons);

        return  view;
    }

    @Override
    public void onStart() {
        super.onStart();
        String username = UserDataDao.getLastUserName();
        List<FavoritePokemonDTO> favorites = FavoritePokemonDao.getFavorites(username);

        favoritePokemonAdapter = new FavoritePokemonAdapter(getActivity(), favorites);

        rvPokemons.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvPokemons.setAdapter(favoritePokemonAdapter);
    }

}
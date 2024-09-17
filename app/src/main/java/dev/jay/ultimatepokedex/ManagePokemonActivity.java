package dev.jay.ultimatepokedex;

import static dev.jay.ultimatepokedex.api.API.BASE_URL;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dev.jay.ultimatepokedex.api.API;
import dev.jay.ultimatepokedex.model.dto.response.SuccessDto;
import dev.jay.ultimatepokedex.model.dto.response.pokemon.Name;
import dev.jay.ultimatepokedex.model.dto.response.pokemon.Pokemon;
import dev.jay.ultimatepokedex.model.dto.response.pokemon.Profile;
import dev.jay.ultimatepokedex.secure_local_db.dao.FavoritePokemonDao;
import dev.jay.ultimatepokedex.secure_local_db.dao.PokemonLocationDao;
import dev.jay.ultimatepokedex.secure_local_db.entity.dto.PokemonLocationDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManagePokemonActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText editEngName, editAbilityOne, editAbilityTwo;

    int pokemonId;
    Pokemon pokemon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_pokemon);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editEngName = findViewById(R.id.editEngName);
        editAbilityOne = findViewById(R.id.editAbilityOne);
        editAbilityTwo = findViewById(R.id.editAbilityTwo);

        findViewById(R.id.btnSave).setOnClickListener(view -> {
            String engName = editEngName.getText().toString();
            String abilityOne = editAbilityOne.getText().toString();
            String abilityTwo = editAbilityTwo.getText().toString();

            if(pokemon != null) {
                pokemon.getName().setEnglish(engName);
                pokemon.getProfile().getAbility().get(0).set(0, abilityOne);
                pokemon.getProfile().getAbility().get(1).set(0, abilityTwo);

                API.pokemonApi().updatePokemonById(pokemon.getId(), pokemon)
                        .enqueue(new Callback<SuccessDto>() {
                            @Override
                            public void onResponse(Call<SuccessDto> call, Response<SuccessDto> response) {
                                onSuccess(response);
                            }

                            @Override
                            public void onFailure(Call<SuccessDto> call, Throwable t) {

                            }
                        });
            } else {
                ArrayList<ArrayList<String>> abilities = new ArrayList<>();
                abilities.add(new ArrayList<>(Arrays.asList(abilityOne, "true")));
                abilities.add(new ArrayList<>(Arrays.asList(abilityTwo, "false")));
                Pokemon newPokemon = new Pokemon(PokemonLocationDao.getLastId() + 1,  new Name(engName), new Profile(abilities));
                API.pokemonApi().addPokemon(newPokemon).enqueue(new Callback<SuccessDto>() {
                    @Override
                    public void onResponse(Call<SuccessDto> call, Response<SuccessDto> response) {
                        onSuccess(response);
                    }

                    @Override
                    public void onFailure(Call<SuccessDto> call, Throwable t) {

                    }
                });

            }

        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        pokemonId = getIntent().getIntExtra("id", 0);
        getSupportActionBar().setTitle(pokemonId == 0? "Create New Pokemon" : "Edit Pokemon Details");
        if(pokemonId != 0) {

            API.pokemonApi().getPokemonById(pokemonId).enqueue(new Callback<Pokemon>() {
                @Override
                public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                    if (response.isSuccessful()) {
                        pokemon = response.body();
                        if (pokemon != null) {


                            String englishName = pokemon.getName().getEnglish();
                            editEngName.setText(englishName);

//                            ArrayList<String> strTypes = pokemon.getType();
//                            editAbilityOne.setText(strTypes.get(0));
//                            if (strTypes.size() > 1) {
//                                editAbilityTwo.setText(strTypes.get(0));
//                            }

                            ArrayList<ArrayList<String>> ability = pokemon.getProfile().getAbility();
                            editAbilityOne.setText(ability.get(0).get(0));
                            editAbilityTwo.setText(ability.get(1).get(0));

//                                Location location = pokemon.getLocation();
//                                if (pokemon.getLocation() == null) {
//                                    pokemon.setLocation(locationGenerator.getRandomLocation());
//                                    location = pokemon.getLocation();
//                                }
//                            PokemonLocationDTO pokemonLocationDTO = PokemonLocationDao.getLocation(pokemon.getId());
//                            LatLng latlng = new LatLng(pokemonLocationDTO.getLatitude(), pokemonLocationDTO.getLongitude());
//
//                            Glide.with(getApplicationContext())
//                                    .asBitmap()
//                                    .load(BASE_URL.substring(0, BASE_URL.length() - 1) + pokemon.getImage().getSprite())
//                                    .apply(new RequestOptions().override(120, 120))
//                                    .into(new CustomTarget<Bitmap>() {
//                                        @Override
//                                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//                                            MarkerOptions markerOptions = new MarkerOptions()
//                                                    .position(latlng)
//                                                    .anchor(0.5f, 0.5f) // default is 0.5f, 1.0f
//                                                    .icon(BitmapDescriptorFactory.fromBitmap(resource))
//                                                    .draggable(true)
//                                                    .title(englishName);
//                                            googleMap.addMarker(markerOptions);
//                                        }
//
//                                        @Override
//                                        public void onLoadCleared(@Nullable Drawable placeholder) {
//                                        }
//                                    });
//
//                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 16), 1500, null);
                        }
                    }
                }

                @Override
                public void onFailure(Call<Pokemon> call, Throwable t) {

                }
            });
        }
    }

    private void onSuccess(Response<SuccessDto> response) {
        if(response.isSuccessful()) {
            SuccessDto successDto = response.body();
            if(successDto != null) {
                try {
                    if(pokemon != null) {
                        FavoritePokemonDao.updateFavoriteName(pokemonId, pokemon.getName().getEnglish());
                    }
                    Toast.makeText(getApplicationContext(), successDto.getMessage(), Toast.LENGTH_LONG).show();
                    Thread.sleep(500);
                    finish();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        getOnBackPressedDispatcher().onBackPressed();
        return true;
    }
}
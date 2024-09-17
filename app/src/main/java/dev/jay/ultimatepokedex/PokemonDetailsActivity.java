package dev.jay.ultimatepokedex;

import static dev.jay.ultimatepokedex.api.API.BASE_URL;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import dev.jay.ultimatepokedex.api.API;
import dev.jay.ultimatepokedex.model.dto.response.pokemon.Location;
import dev.jay.ultimatepokedex.model.dto.response.pokemon.Pokemon;
import dev.jay.ultimatepokedex.secure_local_db.dao.PokemonLocationDao;
import dev.jay.ultimatepokedex.secure_local_db.entity.dto.PokemonLocationDTO;
import dev.jay.ultimatepokedex.utils.LocationGenerator;
import dev.jay.ultimatepokedex.utils.PokemonTypes;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PokemonDetailsActivity extends AppCompatActivity {

    private GoogleMap googleMap;
    private SupportMapFragment supportMapFragment;

    private ImageView ivHiRes, ivTypeOne, ivTypeTwo;
    private TextView tvName, tvTypeOne, tvTypeTwo, tvAbilityOne, tvAbilityTwo;

    private final PokemonTypes pokemonTypes = new PokemonTypes();
    private final LocationGenerator locationGenerator = new LocationGenerator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pokemon_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        ivHiRes = findViewById(R.id.ivHiRes);
        ivTypeOne = findViewById(R.id.ivTypeOne);
        ivTypeTwo = findViewById(R.id.ivTypeTwo);

        tvName = findViewById(R.id.tvName);
        tvTypeOne = findViewById(R.id.tvTypeOne);
        tvTypeTwo = findViewById(R.id.tvTypeTwo);
        tvAbilityOne = findViewById(R.id.tvAbilityOne);
        tvAbilityTwo = findViewById(R.id.tvAbilityTwo);

        findViewById(R.id.ivBack).setOnClickListener(view -> finish());
    }

    @Override
    protected void onStart() {
        super.onStart();
        int id = getIntent().getIntExtra("id", 0);
        if(id != 0) {
            supportMapFragment.getMapAsync(googleMap -> {
                this.googleMap = googleMap;


                API.pokemonApi().getPokemonById(id).enqueue(new Callback<Pokemon>() {
                    @Override
                    public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                        if (response.isSuccessful()) {
                            Pokemon pokemon = response.body();
                            if (pokemon != null) {
                                Glide
                                        .with(getApplicationContext())
                                        .load(BASE_URL.substring(0, BASE_URL.length() - 1) + pokemon.getImage().getHiRes())
                                        .into(ivHiRes);

                                String englishName = pokemon.getName().getEnglish();
                                tvName.setText(englishName);

                                ArrayList<String> types = pokemon.getType();
                                ivTypeOne.setImageResource(pokemonTypes.getTypeIcon(types.get(0)));
                                if (types.size() > 1) {
                                    ivTypeTwo.setImageResource(pokemonTypes.getTypeIcon(types.get(1)));
                                }

                                ArrayList<String> strTypes = pokemon.getType();
                                tvTypeOne.setText(strTypes.get(0));
                                if (strTypes.size() > 1) {
                                    tvTypeTwo.setText(strTypes.get(1));
                                }

                                ArrayList<ArrayList<String>> ability = pokemon.getProfile().getAbility();
                                tvAbilityOne.setText(ability.get(0).get(0));
                                tvAbilityTwo.setText(ability.get(1).get(0));

//                                Location location = pokemon.getLocation();
//                                if (pokemon.getLocation() == null) {
//                                    pokemon.setLocation(locationGenerator.getRandomLocation());
//                                    location = pokemon.getLocation();
//                                }
                                PokemonLocationDTO pokemonLocationDTO = PokemonLocationDao.getLocation(pokemon.getId());
                                LatLng latlng = new LatLng(pokemonLocationDTO.getLatitude(), pokemonLocationDTO.getLongitude());

                                Glide.with(getApplicationContext())
                                        .asBitmap()
                                        .load(BASE_URL.substring(0, BASE_URL.length() - 1) + pokemon.getImage().getSprite())
                                        .apply(new RequestOptions().override(120, 120))
                                        .into(new CustomTarget<Bitmap>() {
                                            @Override
                                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                                MarkerOptions markerOptions = new MarkerOptions()
                                                        .position(latlng)
                                                        .anchor(0.5f, 0.5f) // default is 0.5f, 1.0f
                                                        .icon(BitmapDescriptorFactory.fromBitmap(resource))
                                                        .draggable(true)
                                                        .title(englishName);
                                                googleMap.addMarker(markerOptions);
                                            }

                                            @Override
                                            public void onLoadCleared(@Nullable Drawable placeholder) {
                                            }
                                        });

                                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 16), 1500, null);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Pokemon> call, Throwable t) {

                    }
                });
            });
        }
    }

    private BitmapDescriptor vectorToBitmap(@DrawableRes int id) {
        Drawable vectorDrawable = ResourcesCompat.getDrawable(getResources(), id, null);
        Bitmap bitmap = Bitmap.createBitmap(80,
               80, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}
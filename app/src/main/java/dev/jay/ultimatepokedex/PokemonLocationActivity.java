package dev.jay.ultimatepokedex;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

public class PokemonLocationActivity extends AppCompatActivity {

    private GoogleMap googleMap;
    private SupportMapFragment supportMapFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pokemon_location);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
    }

    @Override
    protected void onStart() {
        super.onStart();
        supportMapFragment.getMapAsync(googleMap -> {
            this.googleMap = googleMap;


            Glide.with(getApplicationContext())
                    .asBitmap()
                  //  .load(BASE_URL.substring(0, BASE_URL.length() - 1) + pokemon.getImage().getSprite())
                    .apply(new RequestOptions().override(120, 120))
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//                            MarkerOptions markerOptions = new MarkerOptions()
//                                    .position(latlng)
//                                    .anchor(0.5f, 0.5f) // default is 0.5f, 1.0f
//                                    .icon(BitmapDescriptorFactory.fromBitmap(resource))
//                                    .draggable(true)
//                                    .title(englishName);
//                            googleMap.addMarker(markerOptions);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {}
                    });

          //  googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 18), 1500, null);

        });
    }
}
package dev.jay.ultimatepokedex.utils;

import java.util.HashMap;
import java.util.Map;

import dev.jay.ultimatepokedex.R;

public class PokemonTypes {
    private final Map<String, Integer> types = new HashMap<>();

    public PokemonTypes() {
        types.put("grass", R.drawable.type_grass);
        types.put("fire", R.drawable.type_fire);
        types.put("water", R.drawable.type_water);
        types.put("poison", R.drawable.type_poison);
        types.put("flying", R.drawable.type_flying);
        types.put("bug", R.drawable.type_bug);
        types.put("normal", R.drawable.type_normal);
        types.put("electric", R.drawable.type_electric);
        types.put("ground", R.drawable.type_ground);
        types.put("rock", R.drawable.type_rock);
        types.put("ghost", R.drawable.type_ghost);
        types.put("psychic", R.drawable.type_psychic);
        types.put("fighting", R.drawable.type_fighting);
        types.put("fairy", R.drawable.type_fairy);
    }



    public int getTypeIcon(String type) {
        return types.getOrDefault(type.toLowerCase(), R.drawable.pokeball_pokemon_svgrepo_com);
    }
}

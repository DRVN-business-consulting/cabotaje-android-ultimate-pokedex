package dev.jay.ultimatepokedex.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dev.jay.ultimatepokedex.model.dto.response.pokemon.Location;

public class LocationGenerator {

    // Create an instance of Random
    Random random = new Random();
    private final List<Location> locations = new ArrayList<>();

    public LocationGenerator() {
        locations.add(new Location(14.425477, 120.974636));
        locations.add(new Location(14.534052, 120.986377));
        locations.add(new Location(9.316222, 123.311561));
        locations.add(new Location(14.590155, 121.02565));
        locations.add(new Location(10.30662, 123.909956));
        locations.add(new Location(10.338414, 123.911775));
        locations.add(new Location(14.653991, 121.029832));
        locations.add(new Location(14.43418, 121.012634));
        locations.add(new Location(16.406007, 120.601003));
        locations.add(new Location(7.070554, 125.607556));
        locations.add(new Location(14.551671, 121.021757));
        locations.add(new Location(7.098989, 125.624827));
        locations.add(new Location(8.233085, 124.245353));
    }

    public  Location getRandomLocation() {
        // Get a random index
        return locations.get(random.nextInt(locations.size()));
    }

}

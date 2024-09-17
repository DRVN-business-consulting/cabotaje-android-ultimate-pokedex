package dev.jay.ultimatepokedex.model.dto.response.pokemon;

import java.util.ArrayList;

public class Pokemon {
    private Integer id;
    private Name name;
    private ArrayList<String> type;
    private Base base;
    private String species;
    private String description;
    private Evolution evolution;
    private Profile profile;
    private Image image;
    private Location location;


    public Pokemon(Integer id, Name name, Profile profile) {
        this.id = id;
        this.name = name;
        this.profile = profile;
    }

    public Pokemon(Integer id, Name name, ArrayList<String> type, Base base, String species, String description, Evolution evolution, Profile profile, Image image, Location location) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.base = base;
        this.species = species;
        this.description = description;
        this.evolution = evolution;
        this.profile = profile;
        this.image = image;
        this.location = location;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public ArrayList<String> getType() {
        return type;
    }

    public void setType(ArrayList<String> type) {
        this.type = type;
    }

    public Base getBase() {
        return base;
    }

    public void setBase(Base base) {
        this.base = base;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Evolution getEvolution() {
        return evolution;
    }

    public void setEvolution(Evolution evolution) {
        this.evolution = evolution;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "id=" + id +
                ", name=" + name +
                ", type=" + type +
                ", base=" + base +
                ", species='" + species + '\'' +
                ", description='" + description + '\'' +
                ", evolution=" + evolution +
                ", profile=" + profile +
                ", image=" + image +
                ", location=" + location +
                '}';
    }
}



package dev.jay.ultimatepokedex.model.dto.response.pokemon;

import java.util.ArrayList;

public class Profile{
    public String height;
    public String weight;
    public ArrayList<String> egg;
    public ArrayList<ArrayList<String>> ability;
    public String gender;

    public Profile(ArrayList<ArrayList<String>> ability) {
        this.ability = ability;
    }

    public Profile(String height, String weight, ArrayList<String> egg, ArrayList<ArrayList<String>> ability, String gender) {
        this.height = height;
        this.weight = weight;
        this.egg = egg;
        this.ability = ability;
        this.gender = gender;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public ArrayList<String> getEgg() {
        return egg;
    }

    public void setEgg(ArrayList<String> egg) {
        this.egg = egg;
    }

    public ArrayList<ArrayList<String>> getAbility() {
        return ability;
    }

    public void setAbility(ArrayList<ArrayList<String>> ability) {
        this.ability = ability;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "height='" + height + '\'' +
                ", weight='" + weight + '\'' +
                ", egg=" + egg +
                ", ability=" + ability +
                ", gender='" + gender + '\'' +
                '}';
    }
}

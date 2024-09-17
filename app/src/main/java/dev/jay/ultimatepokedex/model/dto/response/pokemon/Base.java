package dev.jay.ultimatepokedex.model.dto.response.pokemon;

import com.google.gson.annotations.SerializedName;

public class Base {
    @SerializedName("HP")
    public int hP;
    @SerializedName("Attack")
    public int attack;
    @SerializedName("Defense")
    public int defense;
    @SerializedName("Sp. Attack")
    public int spAttack;
    @SerializedName("Sp. Defense")
    public int spDefense;
    @SerializedName("Speed")
    public int speed;

    public Base(int hP, int attack, int defense, int spAttack, int spDefense, int speed) {
        this.hP = hP;
        this.attack = attack;
        this.defense = defense;
        this.spAttack = spAttack;
        this.spDefense = spDefense;
        this.speed = speed;
    }

    public int gethP() {
        return hP;
    }

    public void sethP(int hP) {
        this.hP = hP;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getSpAttack() {
        return spAttack;
    }

    public void setSpAttack(int spAttack) {
        this.spAttack = spAttack;
    }

    public int getSpDefense() {
        return spDefense;
    }

    public void setSpDefense(int spDefense) {
        this.spDefense = spDefense;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public String toString() {
        return "Base{" +
                "hP=" + hP +
                ", attack=" + attack +
                ", defense=" + defense +
                ", spAttack=" + spAttack +
                ", spDefense=" + spDefense +
                ", speed=" + speed +
                '}';
    }
}

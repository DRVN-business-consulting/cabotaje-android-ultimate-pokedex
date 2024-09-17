package dev.jay.ultimatepokedex.secure_local_db.entity.dto;

import android.database.Cursor;

import dev.jay.ultimatepokedex.secure_local_db.contract.PokemonLocationContract;

public class PokemonLocationDTO {
    private int id;
    private double latitude;
    private double longitude;

    public PokemonLocationDTO(int id, double latitude, double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public static PokemonLocationDTO fromCursor(Cursor cursor) {
        int itemId = cursor.getInt(cursor.getColumnIndexOrThrow(PokemonLocationContract.PokemonLocationEntry._ID));
        double latitude = cursor.getDouble(cursor.getColumnIndexOrThrow(PokemonLocationContract.PokemonLocationEntry.COLUMN_LATITUDE));
        double longitude = cursor.getDouble(cursor.getColumnIndexOrThrow(PokemonLocationContract.PokemonLocationEntry.COLUMN_LONGITUDE));

        return new PokemonLocationDTO(itemId, latitude, longitude);
    }

    @Override
    public String toString() {
        return "PokemonLocationDTO{" +
                "id=" + id +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}

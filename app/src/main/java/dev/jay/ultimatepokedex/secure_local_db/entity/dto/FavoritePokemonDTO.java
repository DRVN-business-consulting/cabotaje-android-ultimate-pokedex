package dev.jay.ultimatepokedex.secure_local_db.entity.dto;

import android.database.Cursor;

import dev.jay.ultimatepokedex.secure_local_db.contract.FavoritePokemonContact;
import dev.jay.ultimatepokedex.secure_local_db.contract.PokemonLocationContract;

public class FavoritePokemonDTO {
    private int id;
    private String name;
    private String thumbURL;
    private String username;
    private long createdAt;
    private long updatedAt;

    public FavoritePokemonDTO(int id, String name, String thumbURL, String username){
        this.id = id;
        this.name = name;
        this.thumbURL = thumbURL;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbURL() {
        return thumbURL;
    }

    public void setThumbURL(String thumbURL) {
        this.thumbURL = thumbURL;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public static FavoritePokemonDTO fromCursor(Cursor cursor) {
        int itemId = cursor.getInt(cursor.getColumnIndexOrThrow(FavoritePokemonContact.FavoritePokemonEntry._ID));
        String name = cursor.getString(cursor.getColumnIndexOrThrow(FavoritePokemonContact.FavoritePokemonEntry.COLUMN_NAME));
        String thumbURL = cursor.getString(cursor.getColumnIndexOrThrow(FavoritePokemonContact.FavoritePokemonEntry.COLUMN_THUMB_URL));
        String username = cursor.getString(cursor.getColumnIndexOrThrow(FavoritePokemonContact.FavoritePokemonEntry.COLUMN_USERNAME));

        return new FavoritePokemonDTO(itemId, name, thumbURL, username);
    }
}


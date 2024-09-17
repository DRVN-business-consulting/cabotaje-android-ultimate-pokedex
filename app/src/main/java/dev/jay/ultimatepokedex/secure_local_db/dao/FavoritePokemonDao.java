package dev.jay.ultimatepokedex.secure_local_db.dao;

import android.content.ContentValues;
import android.database.Cursor;

import net.zetetic.database.sqlcipher.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import dev.jay.ultimatepokedex.model.dto.response.pokemon.Location;
import dev.jay.ultimatepokedex.secure_local_db.contract.FavoritePokemonContact;
import dev.jay.ultimatepokedex.secure_local_db.contract.PokemonLocationContract;
import dev.jay.ultimatepokedex.secure_local_db.entity.dto.FavoritePokemonDTO;
import dev.jay.ultimatepokedex.secure_local_db.entity.dto.PokemonLocationDTO;
import dev.jay.ultimatepokedex.secure_local_db.helper.SecureDBHelper;

public class FavoritePokemonDao {
    public static boolean addToFavorite(int pokemonId, String name, String thumbURL, String username) {
        SQLiteDatabase db = SecureDBHelper.getInstance().getSecureDatabase();

        long creationTime = System.currentTimeMillis();
        ContentValues values = new ContentValues();
        values.put(FavoritePokemonContact.FavoritePokemonEntry._ID, pokemonId);
        values.put(FavoritePokemonContact.FavoritePokemonEntry.COLUMN_NAME, name);
        values.put(FavoritePokemonContact.FavoritePokemonEntry.COLUMN_THUMB_URL, thumbURL);
        values.put(FavoritePokemonContact.FavoritePokemonEntry.COLUMN_USERNAME, username);
        values.put(FavoritePokemonContact.FavoritePokemonEntry.COLUMN_CREATED_AT, creationTime);
        values.put(FavoritePokemonContact.FavoritePokemonEntry.COLUMN_UPDATED_AT, creationTime);
        long id = db.insert(FavoritePokemonContact.FavoritePokemonEntry.TABLE_NAME, null, values);
        return id != -1;
    }

    public static boolean updateFavoriteName(int pokemonId, String name) {
        SQLiteDatabase db = SecureDBHelper.getInstance().getSecureDatabase();

        ContentValues values = new ContentValues();
        values.put(FavoritePokemonContact.FavoritePokemonEntry._ID, pokemonId);
        values.put(FavoritePokemonContact.FavoritePokemonEntry.COLUMN_NAME, name);
        long id = db.update(FavoritePokemonContact.FavoritePokemonEntry.TABLE_NAME, values, FavoritePokemonContact.FavoritePokemonEntry._ID + " = ?", new String[]{String.valueOf(pokemonId)});
        return id > 0;
    }
    public static boolean isFavorite(int pokemonId,  String username) {
        SQLiteDatabase db = SecureDBHelper.getInstance().getSecureDatabase();

        String[] projection = {
                FavoritePokemonContact.FavoritePokemonEntry._ID,
        };

        String selection = FavoritePokemonContact.FavoritePokemonEntry._ID + " = ? AND " + FavoritePokemonContact.FavoritePokemonEntry.COLUMN_USERNAME + " = ?";
        String[] selectionArgs ={String.valueOf(pokemonId), username};

        Cursor cursor = db.query(
                FavoritePokemonContact.FavoritePokemonEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        boolean isFavorite = cursor.getCount() > 0;
        cursor.close();

        return  isFavorite;
    }

    public static boolean removeToFavorite(int pokemonId,  String username) {
        SQLiteDatabase db = SecureDBHelper.getInstance().getSecureDatabase();

        long id = db.delete(FavoritePokemonContact.FavoritePokemonEntry.TABLE_NAME, FavoritePokemonContact.FavoritePokemonEntry._ID + " = ? AND " + FavoritePokemonContact.FavoritePokemonEntry.COLUMN_USERNAME + " = ?", new String[]{String.valueOf(pokemonId), username});
        return id != -1;
    }

    public static List<FavoritePokemonDTO> getFavorites(String username) {
        // Get users
        SQLiteDatabase db = SecureDBHelper.getInstance().getSecureDatabase();

        String[] projection = {
                FavoritePokemonContact.FavoritePokemonEntry._ID,
                FavoritePokemonContact.FavoritePokemonEntry.COLUMN_NAME,
                FavoritePokemonContact.FavoritePokemonEntry.COLUMN_THUMB_URL,
                FavoritePokemonContact.FavoritePokemonEntry.COLUMN_USERNAME
        };

        String selection = FavoritePokemonContact.FavoritePokemonEntry.COLUMN_USERNAME + " = ?";
        String[] selectionArgs ={username};

        Cursor cursor = db.query(
                FavoritePokemonContact.FavoritePokemonEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        List<FavoritePokemonDTO> favorites = new ArrayList<>();

        while (cursor.moveToNext()) {
            favorites.add(FavoritePokemonDTO.fromCursor(cursor));
        }

        cursor.close();
        return favorites;
    }

}

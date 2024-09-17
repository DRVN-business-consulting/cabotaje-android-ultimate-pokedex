package dev.jay.ultimatepokedex.secure_local_db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.util.Log;

import androidx.annotation.NonNull;

import net.zetetic.database.sqlcipher.SQLiteDatabase;

import dev.jay.ultimatepokedex.model.dto.response.pokemon.Location;
import dev.jay.ultimatepokedex.secure_local_db.contract.PokemonLocationContract;
import dev.jay.ultimatepokedex.secure_local_db.contract.UserAccessTokenContract;
import dev.jay.ultimatepokedex.secure_local_db.entity.dto.PokemonLocationDTO;
import dev.jay.ultimatepokedex.secure_local_db.helper.SecureDBHelper;
import dev.jay.ultimatepokedex.utils.LocationGenerator;

public class PokemonLocationDao {
    private  static final LocationGenerator locationGenerator = new LocationGenerator();
    public PokemonLocationDao() {}

    public static boolean insertLocation(int pokemonId) {
        SQLiteDatabase db = SecureDBHelper.getInstance().getSecureDatabase();

        String[] projection = {
            PokemonLocationContract.PokemonLocationEntry._ID,
        };

        String selection = PokemonLocationContract.PokemonLocationEntry._ID + " = ?";
        String[] selectionArgs = { String.valueOf(pokemonId) };

        Cursor cursor = db.query(
                PokemonLocationContract.PokemonLocationEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if(cursor.getCount() == 0) {
            cursor.close();

            Location location = locationGenerator.getRandomLocation();
            long creationTime = System.currentTimeMillis();
            ContentValues values = new ContentValues();
            values.put(PokemonLocationContract.PokemonLocationEntry._ID, pokemonId);
            values.put(PokemonLocationContract.PokemonLocationEntry.COLUMN_LATITUDE, location.getLatitude());
            values.put(PokemonLocationContract.PokemonLocationEntry.COLUMN_LONGITUDE, location.getLongitude());
            values.put(PokemonLocationContract.PokemonLocationEntry.COLUMN_CREATED_AT, creationTime);
            values.put(PokemonLocationContract.PokemonLocationEntry.COLUMN_UPDATED_AT, creationTime);
            long id = db.insert(PokemonLocationContract.PokemonLocationEntry.TABLE_NAME, null, values);
            return id != -1;
        } else {
            cursor.close();
            return  true;
        }


    }

    public static PokemonLocationDTO getLocation(int pokemonId) {
        // Get users
        SQLiteDatabase db = SecureDBHelper.getInstance().getSecureDatabase();

        String[] projection = {
                PokemonLocationContract.PokemonLocationEntry._ID,
                PokemonLocationContract.PokemonLocationEntry.COLUMN_LATITUDE,
                PokemonLocationContract.PokemonLocationEntry.COLUMN_LONGITUDE,
        };

        String selection = PokemonLocationContract.PokemonLocationEntry._ID + " = ?";
        String[] selectionArgs = { String.valueOf(pokemonId) };

        Cursor cursor = db.query(
                PokemonLocationContract.PokemonLocationEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        PokemonLocationDTO pokemonLocationDTO = null;
        if(cursor.moveToNext()) {
            pokemonLocationDTO = PokemonLocationDTO.fromCursor(cursor);
        }

        cursor.close();
        return pokemonLocationDTO;
    }

    public static int getLastId() {
        SQLiteDatabase db = SecureDBHelper.getInstance().getSecureDatabase();
        Cursor cursor = db.rawQuery("SELECT MAX(" + PokemonLocationContract.PokemonLocationEntry._ID + ") FROM " + PokemonLocationContract.PokemonLocationEntry.TABLE_NAME, null);

        int id = 0;
        if(cursor.moveToNext()) {
            id = cursor.getInt(0);
        }


        cursor.close();

        return id;
    }

}

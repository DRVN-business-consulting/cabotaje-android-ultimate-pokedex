package dev.jay.ultimatepokedex.secure_local_db.contract;

import android.provider.BaseColumns;

public class PokemonLocationContract {
    public PokemonLocationContract() {}

    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + PokemonLocationEntry.TABLE_NAME + " (" +
                    PokemonLocationEntry._ID + " INTEGER PRIMARY KEY," +
                    PokemonLocationEntry.COLUMN_LATITUDE + " INTEGER," +
                    PokemonLocationEntry.COLUMN_LONGITUDE + " INTEGER," +
                    PokemonLocationEntry.COLUMN_CREATED_AT + " INTEGER," +
                    PokemonLocationEntry.COLUMN_UPDATED_AT + " INTEGER" +
                    ")";

    public static final String SQL_DROP_TABLE =
            "DROP TABLE IF EXISTS " + PokemonLocationEntry.TABLE_NAME;

    public static class PokemonLocationEntry implements BaseColumns {
        public static final String TABLE_NAME = "pokemon_locations";
        public static final String COLUMN_LATITUDE = "latitude";
        public static final String COLUMN_LONGITUDE = "longitude";
        public static final String COLUMN_CREATED_AT = "created_at";
        public static final String COLUMN_UPDATED_AT = "updated_at";
    }
}

package dev.jay.ultimatepokedex.secure_local_db.contract;

import android.provider.BaseColumns;

public class FavoritePokemonContact {
    public FavoritePokemonContact() {}
    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + FavoritePokemonContact.FavoritePokemonEntry.TABLE_NAME + " (" +
                    FavoritePokemonContact.FavoritePokemonEntry._ID + " INTEGER PRIMARY KEY," +
                    FavoritePokemonContact.FavoritePokemonEntry.COLUMN_NAME + " TEXT," +
                    FavoritePokemonContact.FavoritePokemonEntry.COLUMN_THUMB_URL + " TEXT," +
                    FavoritePokemonContact.FavoritePokemonEntry.COLUMN_USERNAME + " TEXT," +
                    FavoritePokemonContact.FavoritePokemonEntry.COLUMN_CREATED_AT + " INTEGER," +
                    FavoritePokemonContact.FavoritePokemonEntry.COLUMN_UPDATED_AT + " INTEGER" +
                    ")";

    public static final String SQL_DROP_TABLE =
            "DROP TABLE IF EXISTS " + FavoritePokemonContact.FavoritePokemonEntry.TABLE_NAME;

    public static class FavoritePokemonEntry implements BaseColumns {
        public static final String TABLE_NAME = "favorite_pokemons";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_THUMB_URL = "thumb_url";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_CREATED_AT = "created_at";
        public static final String COLUMN_UPDATED_AT = "updated_at";
    }
}

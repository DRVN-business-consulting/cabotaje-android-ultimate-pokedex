package dev.jay.ultimatepokedex.secure_local_db.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

import java.io.File;

import dev.jay.ultimatepokedex.secure_local_db.contract.FavoritePokemonContact;
import dev.jay.ultimatepokedex.secure_local_db.contract.PokemonLocationContract;
import dev.jay.ultimatepokedex.secure_local_db.contract.UserAccessTokenContract;
import dev.jay.ultimatepokedex.secure_local_db.contract.UserDataContract;

public class SecureDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "app.db";

    private static net.zetetic.database.sqlcipher.SQLiteDatabase secureDatabase;

    private static SecureDBHelper instance;
    public static void initialize(@NonNull Context context) {
        instance = new SecureDBHelper(context);
    }

    public static SecureDBHelper getInstance() {
        return instance;
    }

    private SecureDBHelper(@NonNull Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        String password = "pokemon";
        File databaseFile = context.getDatabasePath("secure.db");
        secureDatabase = net.zetetic.database.sqlcipher.SQLiteDatabase.openOrCreateDatabase(databaseFile, password, null, null, null);
        secureDatabase.execSQL(UserAccessTokenContract.SQL_CREATE_TABLE);
        secureDatabase.execSQL(UserDataContract.SQL_CREATE_TABLE);
        secureDatabase.execSQL(PokemonLocationContract.SQL_CREATE_TABLE);
        secureDatabase.execSQL(FavoritePokemonContact.SQL_CREATE_TABLE);
    }

    public net.zetetic.database.sqlcipher.SQLiteDatabase getSecureDatabase() {
        return secureDatabase;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create our databases
        db.execSQL(UserAccessTokenContract.SQL_CREATE_TABLE);
        db.execSQL(UserDataContract.SQL_CREATE_TABLE);
        db.execSQL(PokemonLocationContract.SQL_CREATE_TABLE);
        db.execSQL(FavoritePokemonContact.SQL_CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Migrate our databases
        db.execSQL(UserAccessTokenContract.SQL_DROP_TABLE);
        db.execSQL(UserDataContract.SQL_DROP_TABLE);
        db.execSQL(PokemonLocationContract.SQL_DROP_TABLE);
        db.execSQL(FavoritePokemonContact.SQL_DROP_TABLE);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}

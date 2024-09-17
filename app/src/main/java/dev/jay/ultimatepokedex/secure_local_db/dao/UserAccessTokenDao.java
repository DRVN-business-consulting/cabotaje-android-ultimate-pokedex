package dev.jay.ultimatepokedex.secure_local_db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import net.zetetic.database.sqlcipher.SQLiteDatabase;

import dev.jay.ultimatepokedex.secure_local_db.contract.PokemonLocationContract;
import dev.jay.ultimatepokedex.secure_local_db.contract.UserAccessTokenContract;
import dev.jay.ultimatepokedex.secure_local_db.entity.dto.PokemonLocationDTO;
import dev.jay.ultimatepokedex.secure_local_db.helper.SecureDBHelper;

public class UserAccessTokenDao {
    public static boolean deleteLastToken() {
        SQLiteDatabase db = SecureDBHelper.getInstance().getSecureDatabase();
        int res = db.delete(UserAccessTokenContract.UserAccessTokenEntry.TABLE_NAME, null, null);
        Log.d("deleteLastToken", res + " >< ");
        return  res != -1;
    }
    public static boolean insertToken(String accessToken) {
        if(! deleteLastToken()) return false;

        Log.d("insertToken", accessToken);

        SQLiteDatabase db = SecureDBHelper.getInstance().getSecureDatabase();
        long createdAt = System.currentTimeMillis();
        ContentValues values = new ContentValues();
        values.put(UserAccessTokenContract.UserAccessTokenEntry.COLUMN_ACCESS_TOKEN, accessToken);
        values.put(UserAccessTokenContract.UserAccessTokenEntry.COLUMN_CREATED_AT, createdAt);

        long id = db.insert(UserAccessTokenContract.UserAccessTokenEntry.TABLE_NAME, null, values);
        return id != -1;
    }

    public static String getLastToken() {
        SQLiteDatabase db = SecureDBHelper.getInstance().getSecureDatabase();

        String[] projection = {
                UserAccessTokenContract.UserAccessTokenEntry.COLUMN_ACCESS_TOKEN
        };

        Cursor cursor = db.query(
                UserAccessTokenContract.UserAccessTokenEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null,
                null
        );

        String token = null;
        if(cursor.moveToNext()) {
            token = cursor.getString(cursor.getColumnIndexOrThrow(UserAccessTokenContract.UserAccessTokenEntry.COLUMN_ACCESS_TOKEN));
        }

        cursor.close();

        return token;
    }
}

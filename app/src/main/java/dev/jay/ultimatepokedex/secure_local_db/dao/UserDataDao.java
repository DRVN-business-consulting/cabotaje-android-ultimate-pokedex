package dev.jay.ultimatepokedex.secure_local_db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import net.zetetic.database.sqlcipher.SQLiteDatabase;

import dev.jay.ultimatepokedex.secure_local_db.contract.PokemonLocationContract;
import dev.jay.ultimatepokedex.secure_local_db.contract.UserAccessTokenContract;
import dev.jay.ultimatepokedex.secure_local_db.contract.UserDataContract;
import dev.jay.ultimatepokedex.secure_local_db.entity.dto.PokemonLocationDTO;
import dev.jay.ultimatepokedex.secure_local_db.entity.dto.UserDataDTO;
import dev.jay.ultimatepokedex.secure_local_db.helper.SecureDBHelper;

public class UserDataDao {
    public static boolean upsertData(String username) {
        String imageUrl = getImageUrl(username);

        SQLiteDatabase db = SecureDBHelper.getInstance().getSecureDatabase();

        ContentValues values = new ContentValues();
        values.put(UserDataContract.UserDataEntry.COLUMN_USERNAME, username);
        values.put(UserDataContract.UserDataEntry.COLUMN_IMAGE_URL, imageUrl);
        values.put(UserDataContract.UserDataEntry.COLUMN_LAST_LOGIN, System.currentTimeMillis());

        long id = db.replace(UserDataContract.UserDataEntry.TABLE_NAME, null, values);
        Log.d("upsertLastLogin", id + " >>>");
        return id != -1;
    }

    public static String getImageUrl(String username) {
        // Get users
        SQLiteDatabase db = SecureDBHelper.getInstance().getSecureDatabase();

        String[] projection = {
                UserDataContract.UserDataEntry.COLUMN_IMAGE_URL
        };

        String selection = UserDataContract.UserDataEntry.COLUMN_USERNAME + " = ? ";
        String[] selectionArgs = {username};


        Cursor cursor = db.query(
                UserDataContract.UserDataEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        String imageUrl  = null;
        if(cursor.moveToNext()) {
            imageUrl =  cursor.getString(cursor.getColumnIndexOrThrow(UserDataContract.UserDataEntry.COLUMN_IMAGE_URL));
        }

        cursor.close();
        return imageUrl;
    }


    public static String getLastUserName() {
        // Get users
        SQLiteDatabase db = SecureDBHelper.getInstance().getSecureDatabase();

        String[] projection = {
                UserDataContract.UserDataEntry.COLUMN_USERNAME,
        };

        String sortingOrder = UserDataContract.UserDataEntry.COLUMN_LAST_LOGIN + " DESC LIMIT 1";

        Cursor cursor = db.query(
                UserDataContract.UserDataEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortingOrder
        );

        String username  = null;
        if(cursor.moveToNext()) {
            username =  cursor.getString(cursor.getColumnIndexOrThrow(UserDataContract.UserDataEntry.COLUMN_USERNAME));
        }

        cursor.close();
        return username;
    }

    public static boolean updateImageUrl(String username, String imageUrl) {
        SQLiteDatabase db = SecureDBHelper.getInstance().getSecureDatabase();
        ContentValues values = new ContentValues();
        values.put(UserDataContract.UserDataEntry.COLUMN_IMAGE_URL, imageUrl);

        long id = db.update(UserDataContract.UserDataEntry.TABLE_NAME, values, UserDataContract.UserDataEntry.COLUMN_USERNAME + " = ?", new String[]{username});
        return id > 0;
    }

}

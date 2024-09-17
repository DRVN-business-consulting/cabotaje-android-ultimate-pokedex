package dev.jay.ultimatepokedex.secure_local_db.contract;

import android.provider.BaseColumns;

public class UserAccessTokenContract {
    public UserAccessTokenContract() {}
    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + UserAccessTokenEntry.TABLE_NAME + " (" +
                    UserAccessTokenEntry.COLUMN_ACCESS_TOKEN + " TEXT PRIMARY KEY," +
                    UserAccessTokenEntry.COLUMN_CREATED_AT + " INTEGER" +
                    ")";

    public static final String SQL_DROP_TABLE =
            "DROP TABLE IF EXISTS " + UserAccessTokenEntry.TABLE_NAME;

    public static class UserAccessTokenEntry {
        public static final String TABLE_NAME = "user_access_tokens";
        public static final String COLUMN_ACCESS_TOKEN = "access_token";
        public static final String COLUMN_CREATED_AT = "created_at";
    }
}

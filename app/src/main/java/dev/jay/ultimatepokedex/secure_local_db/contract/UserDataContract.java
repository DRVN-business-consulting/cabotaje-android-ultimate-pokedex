package dev.jay.ultimatepokedex.secure_local_db.contract;

public class UserDataContract {
    public UserDataContract() {}

    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + UserDataContract.UserDataEntry.TABLE_NAME + " (" +
                    UserDataEntry.COLUMN_USERNAME + " TEXT PRIMARY KEY," +
                    UserDataEntry.COLUMN_IMAGE_URL + " TEXT," +
                    UserDataEntry.COLUMN_LAST_LOGIN + " INTEGER" +
                    ")";

    public static final String SQL_DROP_TABLE =
            "DROP TABLE IF EXISTS " + UserDataContract.UserDataEntry.TABLE_NAME;

    public static class UserDataEntry {
        public static final String TABLE_NAME = "user_data";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_IMAGE_URL = "image_url";
        public static final String COLUMN_LAST_LOGIN = "last_login";
    }
}

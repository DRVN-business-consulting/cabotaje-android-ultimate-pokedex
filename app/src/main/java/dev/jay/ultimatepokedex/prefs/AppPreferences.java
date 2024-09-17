package dev.jay.ultimatepokedex.prefs;


import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import dev.jay.ultimatepokedex.model.dto.response.UserDto;

public class AppPreferences {

    private static final String PREFS_NAME = "app_prefs";
    private static final String KEY_USER_DATA = "user_data";
    private static AppPreferences instance;
    private static Gson gson = new Gson();

    private SharedPreferences sharedPreferences;

    private AppPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public static void initialize(Context context) {
        if (instance == null) {
            instance = new AppPreferences(context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE));
        }
    }

    public static AppPreferences getInstance() {
        return instance;
    }

    public void setUserData(UserDto userDto) {
        sharedPreferences.edit().putString(KEY_USER_DATA,gson.toJson(userDto)).apply();
    }

    public UserDto getUserData() {
        String userDataJson = sharedPreferences.getString(KEY_USER_DATA, null);
        if (userDataJson == null) {
            return null;
        }
        return gson.fromJson(userDataJson, UserDto.class);
    }

}

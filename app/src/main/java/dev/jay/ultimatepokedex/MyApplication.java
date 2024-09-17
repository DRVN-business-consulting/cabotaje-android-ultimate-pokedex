package dev.jay.ultimatepokedex;

import android.app.Application;

import dev.jay.ultimatepokedex.secure_local_db.helper.SecureDBHelper;

public class MyApplication  extends Application {

    static {
        System.loadLibrary("sqlcipher");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        SecureDBHelper.initialize(this);
    }

}

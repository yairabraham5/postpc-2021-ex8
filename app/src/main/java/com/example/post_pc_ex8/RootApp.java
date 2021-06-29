package com.example.post_pc_ex8;

import android.app.Application;
import android.content.SharedPreferences;

public class RootApp extends Application {

    SharedPreferences sharedPreferences;
    private static RootApp rootApp = null;

    @Override
    public void onCreate() {
        super.onCreate();
        rootApp  = this;
        sharedPreferences = this.getSharedPreferences("localDb", MODE_PRIVATE);
    }

    public static RootApp getInstance() {
        return rootApp;
    }

    public long loadFromSharedPreferences(String id){
        return sharedPreferences.getLong(id, 2);
    }

    public void savedToSharedPreferences(String id, long savedVal){
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putLong(id, savedVal);
        edit.apply();
    }

}

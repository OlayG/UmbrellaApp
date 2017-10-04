package com.example.admin.umbrellaapp.injection.sharedpreference;

import android.content.SharedPreferences;

import javax.inject.Inject;

public class MySharedPreferences {

    private SharedPreferences mSharedPreferences;

    @Inject
    public MySharedPreferences(SharedPreferences mSharedPreferences) {
        this.mSharedPreferences = mSharedPreferences;
    }

    public void putStringData(String key, String data) {
        mSharedPreferences.edit().putString(key,data).apply();
    }

    public String getStringData(String key) {
        return mSharedPreferences.getString(key, null);
    }

    public void putIntData(String key, int data) {
        mSharedPreferences.edit().putInt(key,data).apply();
    }

    public int getIntData(String key) {
        return mSharedPreferences.getInt(key, -0);
    }
}

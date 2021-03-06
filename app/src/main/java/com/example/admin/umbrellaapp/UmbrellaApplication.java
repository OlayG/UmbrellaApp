package com.example.admin.umbrellaapp;

import android.app.Application;

import com.example.admin.umbrellaapp.injection.ContextModule;
import com.example.admin.umbrellaapp.injection.maininjection.DaggerMainActivityComponent;
import com.example.admin.umbrellaapp.injection.maininjection.MainActivityComponent;
import com.example.admin.umbrellaapp.injection.settingsinjection.DaggerSettingsActivityComponent;
import com.example.admin.umbrellaapp.injection.settingsinjection.SettingsActivityComponent;
import com.example.admin.umbrellaapp.injection.sharedpreference.SharedPreferencesModule;

public class UmbrellaApplication extends Application {

    private MainActivityComponent mainComponent;
    private SettingsActivityComponent settingsComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mainComponent = DaggerMainActivityComponent.builder()
                .sharedPreferencesModule(new SharedPreferencesModule())
                .contextModule(new ContextModule(getApplicationContext()))
                .build();

        settingsComponent  = DaggerSettingsActivityComponent.builder()
                .sharedPreferencesModule(new SharedPreferencesModule())
                .contextModule(new ContextModule(getApplicationContext()))
                .build();
    }

    public MainActivityComponent getMainActivityComponent() {
        return mainComponent;
    }

    public SettingsActivityComponent getSettingsComponent() {
        return settingsComponent;
    }
}

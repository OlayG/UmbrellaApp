package com.example.admin.umbrellaapp.injection.settings_activity;

import com.example.admin.umbrellaapp.injection.ContextModule;
import com.example.admin.umbrellaapp.injection.main_activity.MainActivityModule;
import com.example.admin.umbrellaapp.injection.sharedpreference.SharedPreferencesModule;
import com.example.admin.umbrellaapp.view.settings_activity.SettingsActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = { MainActivityModule.class, SharedPreferencesModule.class, ContextModule.class })
public interface SettingsActivityComponent {

    void inject(SettingsActivity settingsActivity);

}

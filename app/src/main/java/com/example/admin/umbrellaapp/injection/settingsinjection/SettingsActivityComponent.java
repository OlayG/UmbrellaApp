package com.example.admin.umbrellaapp.injection.settingsinjection;

import com.example.admin.umbrellaapp.injection.ContextModule;
import com.example.admin.umbrellaapp.injection.sharedpreference.SharedPreferencesModule;
import com.example.admin.umbrellaapp.view.settingsactivity.SettingsActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = { SettingsActivityModule.class, SharedPreferencesModule.class, ContextModule.class })
public interface SettingsActivityComponent {

    void inject(SettingsActivity settingsActivity);

}

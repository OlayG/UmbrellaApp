package com.example.admin.umbrellaapp.injection.settingsinjection;

import com.example.admin.umbrellaapp.injection.ContextModule;
import com.example.admin.umbrellaapp.injection.maininjection.MainActivityModule;
import com.example.admin.umbrellaapp.injection.sharedpreference.SharedPreferencesModule;
import com.example.admin.umbrellaapp.view.settingsactivity.SettingsActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = { MainActivityModule.class, SharedPreferencesModule.class, ContextModule.class })
public interface SettingsActivityComponent {

    void inject(SettingsActivity settingsActivity);

}

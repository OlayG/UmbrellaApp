package com.example.admin.umbrellaapp.injection.settingsinjection;

import com.example.admin.umbrellaapp.view.settingsactivity.SettingsActivityPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class SettingsActivityModule {

    @Provides
    SettingsActivityPresenter presentSettingsActivityPresenter() {
        return new SettingsActivityPresenter();
    }
}

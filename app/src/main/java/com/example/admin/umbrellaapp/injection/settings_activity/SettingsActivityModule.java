package com.example.admin.umbrellaapp.injection.settings_activity;

import com.example.admin.umbrellaapp.view.settings_activity.SettingsActivityPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class SettingsActivityModule {

    @Provides
    SettingsActivityPresenter presentSettingsActivityPresenter() {
        return new SettingsActivityPresenter();
    }
}

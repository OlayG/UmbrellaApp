package com.example.admin.umbrellaapp.injection.main_activity;

import com.example.admin.umbrellaapp.view.main_activity.MainActivityPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {

    @Provides
    MainActivityPresenter providesMainActivityPresenter() {
        return new MainActivityPresenter();
    }
}

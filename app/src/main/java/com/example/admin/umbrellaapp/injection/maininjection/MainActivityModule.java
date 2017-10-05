package com.example.admin.umbrellaapp.injection.maininjection;

import com.example.admin.umbrellaapp.view.mainactivity.MainActivityPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {

    @Provides
    MainActivityPresenter providesMainActivityPresenter() {
        return new MainActivityPresenter();
    }
}

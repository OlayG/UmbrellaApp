package com.example.admin.umbrellaapp.injection.main_activity;

import com.example.admin.umbrellaapp.injection.ContextModule;
import com.example.admin.umbrellaapp.injection.sharedpreference.SharedPreferencesModule;
import com.example.admin.umbrellaapp.view.main_activity.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = { MainActivityModule.class, SharedPreferencesModule.class, ContextModule.class })
public interface MainActivityComponent {

    void inject(MainActivity mainActivity);
}

package com.example.admin.umbrellaapp.injection.maininjection;

import com.example.admin.umbrellaapp.injection.ContextModule;
import com.example.admin.umbrellaapp.injection.sharedpreference.SharedPreferencesModule;
import com.example.admin.umbrellaapp.view.mainactivity.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = { MainActivityModule.class, SharedPreferencesModule.class, ContextModule.class })
public interface MainActivityComponent {

    void inject(MainActivity mainActivity);
}

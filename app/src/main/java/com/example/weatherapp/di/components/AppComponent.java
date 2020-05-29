package com.example.weatherapp.di.components;

import com.example.weatherapp.MainActivity;
import com.example.weatherapp.di.modules.ContextModule;
import com.example.weatherapp.di.modules.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class, ContextModule.class})
public abstract interface AppComponent {

    void inject(MainActivity mainActivity);
}

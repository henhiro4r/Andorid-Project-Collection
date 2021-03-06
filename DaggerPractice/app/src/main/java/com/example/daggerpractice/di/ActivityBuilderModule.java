package com.example.daggerpractice.di;

import com.example.daggerpractice.di.auth.AuthModule;
import com.example.daggerpractice.di.auth.AuthScope;
import com.example.daggerpractice.di.auth.AuthViewModelModule;
import com.example.daggerpractice.di.main.MainFragmentBuilderModule;
import com.example.daggerpractice.di.main.MainModule;
import com.example.daggerpractice.di.main.MainScope;
import com.example.daggerpractice.di.main.MainViewModelModule;
import com.example.daggerpractice.ui.auth.AuthActivity;
import com.example.daggerpractice.ui.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

//Place to put activity or fragment to be injected

@Module
public abstract class ActivityBuilderModule {

    @AuthScope
    @ContributesAndroidInjector(
            modules = {
                    AuthViewModelModule.class,
                    AuthModule.class,
            } //auth activity sub component
    )
    abstract AuthActivity contributeAuthActivity();

    @MainScope
    @ContributesAndroidInjector(
            modules = {
                    MainFragmentBuilderModule.class,
                    MainViewModelModule.class,
                    MainModule.class,
            }
    )
    abstract MainActivity contributeMAinActivity();
}

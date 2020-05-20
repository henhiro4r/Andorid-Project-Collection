package com.example.daggerpractice.di.main;

import com.example.daggerpractice.ui.main.post.PostFragment;
import com.example.daggerpractice.ui.main.profile.ProfileFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainFragmentBuilderModule {

    @ContributesAndroidInjector
    abstract ProfileFragment contributeProfileFragment();

    @ContributesAndroidInjector
    abstract PostFragment contributePostFragment();
}

package com.example.daggerpractice.di.main;

import androidx.lifecycle.ViewModel;

import com.example.daggerpractice.di.ViewModelKey;
import com.example.daggerpractice.ui.main.post.PostViewModel;
import com.example.daggerpractice.ui.main.profile.ProfileViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class MainViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel.class)
    public abstract ViewModel bindProfileVIewModel(ProfileViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(PostViewModel.class)
    public abstract ViewModel bindPostVIewModel(PostViewModel viewModel);
}

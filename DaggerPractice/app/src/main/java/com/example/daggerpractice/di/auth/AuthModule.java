package com.example.daggerpractice.di.auth;

import com.example.daggerpractice.model.User;
import com.example.daggerpractice.network.auth.AuthApi;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class AuthModule {

    @AuthScope // the object will be recreate because when rotating screen activity will destroyed and automatically the scope also destroyed
    @Provides
    @Named("auth_user")
    static User someUser(){
        return new User();
    }

    @AuthScope
    @Provides
    static AuthApi provideAuthApi(Retrofit retrofit){
        return retrofit.create(AuthApi.class);
    }
}

package com.example.daggerpractice.di;

import android.app.Application;

import com.example.daggerpractice.BaseApplication;
import com.example.daggerpractice.SessionManager;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

// AppComponent is a service meanwhile BaseApplication is the client
@Singleton // scope to application wide dependency another explanation is app component owns a singleton scope
@Component(
        modules = {
                AndroidSupportInjectionModule.class,
                ActivityBuilderModule.class, // all module to know what to do
                AppModule.class,
                ViewModelFactoryModule.class,
        }
)
public interface AppComponent extends AndroidInjector<BaseApplication> {

    SessionManager sessionManager();

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}

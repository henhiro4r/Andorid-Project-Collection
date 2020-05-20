package com.example.daggerpractice;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.example.daggerpractice.model.User;
import com.example.daggerpractice.ui.auth.AuthResource;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SessionManager {
    private static final String TAG = "SessionManager";

    private MediatorLiveData<AuthResource<User>> cachedUser = new MediatorLiveData<>();

    @Inject
    public SessionManager() {

    }

    public void authWithId(final LiveData<AuthResource<User>> source) {
        if (cachedUser != null) {
            AuthResource.loading((User) null);
            cachedUser.addSource(source, new Observer<AuthResource<User>>() {
                @Override
                public void onChanged(AuthResource<User> userAuthResource) {
                    cachedUser.setValue(userAuthResource);
                    cachedUser.removeSource(source);
                }
            });
        }
    }

    public LiveData<AuthResource<User>> getUser() {
        return cachedUser;
    }

    public void logout() {
        Log.d(TAG, "logout: logging out");
        cachedUser.setValue(AuthResource.<User>logout());
    }
}

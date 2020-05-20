package com.example.daggerpractice.ui.main.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.daggerpractice.R;
import com.example.daggerpractice.model.User;
import com.example.daggerpractice.ui.auth.AuthResource;
import com.example.daggerpractice.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends DaggerFragment {

    private static final String TAG = "ProfileFragment";
    private ProfileViewModel viewModel;
    private TextView tvEmail, tvUsername, tvWebsite;

    @Inject
    ViewModelProviderFactory providerFactory;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toast.makeText(getActivity(), "Profile Fragment", Toast.LENGTH_SHORT).show();
        tvEmail = view.findViewById(R.id.email);
        tvUsername = view.findViewById(R.id.username);
        tvWebsite = view.findViewById(R.id.website);
        viewModel = new ViewModelProvider(this.getViewModelStore(), providerFactory).get(ProfileViewModel.class);
        getUserData();
    }

    private void getUserData(){
        viewModel.getAuthUser().removeObservers(getViewLifecycleOwner());
        viewModel.getAuthUser().observe(getViewLifecycleOwner(), new Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> userAuthResource) {
                if (userAuthResource != null) {
                    switch (userAuthResource.status) {
                        case AUTHENTICATED:
                            setUserDetails(userAuthResource.data);
                            break;
                        case ERROR:
                            setErrorDetails(userAuthResource.message);
                            break;
                    }
                }
            }
        });
    }

    private void setErrorDetails(String message) {
        tvEmail.setText(message);
        tvUsername.setText(R.string.error);
        tvWebsite.setText(R.string.error);
    }

    private void setUserDetails(User user) {
        tvEmail.setText(user.getEmail());
        tvUsername.setText(user.getUsername());
        tvWebsite.setText(user.getWebsite());
    }
}

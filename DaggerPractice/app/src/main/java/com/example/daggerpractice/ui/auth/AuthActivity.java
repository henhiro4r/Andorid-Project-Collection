package com.example.daggerpractice.ui.auth;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.RequestManager;
import com.example.daggerpractice.R;
import com.example.daggerpractice.model.User;
import com.example.daggerpractice.ui.main.MainActivity;
import com.example.daggerpractice.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class AuthActivity extends DaggerAppCompatActivity implements View.OnClickListener {

    private static final String TAG = "AuthActivity";
    private AuthViewModel viewModel;
    private ProgressBar progressBar;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    Drawable logo;

    @Inject
    RequestManager requestManager;

    private ImageView loginLogo;
    private EditText editUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        loginLogo = findViewById(R.id.login_logo);
        editUserId = findViewById(R.id.user_id_input);
        progressBar = findViewById(R.id.progress_bar);
        Button btnLogin = findViewById(R.id.login_button);
        viewModel = new ViewModelProvider(this.getViewModelStore(), providerFactory).get(AuthViewModel.class);
        btnLogin.setOnClickListener(this);
        setLogo();
        observers();
    }

    private void observers() {
        viewModel.observeAuthState().observe(this, new Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> userAuthResource) {
                if (userAuthResource != null) {
                    switch (userAuthResource.status) {
                        case LOADING:
                            showLoading(true);
                            break;
                        case AUTHENTICATED:
                            Log.d(TAG, "onChanged: " + userAuthResource.data.getEmail());
                            showLoading(false);
                            gotoMain();
                            break;
                        case ERROR:
                            Toast.makeText(AuthActivity.this, userAuthResource.message, Toast.LENGTH_SHORT).show();
                            showLoading(false);
                            break;
                        case NOT_AUTHENTICATED:
                            showLoading(false);
                            break;
                    }
                }
            }
        });
    }

    private void gotoMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void showLoading(boolean isLoading) {
        if (isLoading){
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void setLogo() {
        requestManager
                .load(logo)
                .into(loginLogo);
    }

    @Override
    public void onClick(View view) {
        showLoading(true);
        if (view.getId() == R.id.login_button) {
            login();
        }
    }

    private void login() {
        if (TextUtils.isEmpty(editUserId.getText().toString())) {
            return;
        }
        viewModel.authWithId(Integer.parseInt(editUserId.getText().toString()));
    }
}

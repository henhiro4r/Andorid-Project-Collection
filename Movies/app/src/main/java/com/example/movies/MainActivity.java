package com.example.movies;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.movies.fragment.MovieFragment;
import com.example.movies.fragment.TvShowFragment;
import com.example.movies.preference.Pref;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ActionBar toolbar;
    private Pref pref;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            toolbar.setDisplayShowHomeEnabled(true);
            switch (item.getItemId()) {
                case R.id.nav_movie:
                    toolbar.setIcon(R.drawable.ic_outline_movie);
                    toolbar.setTitle(" " + getString(R.string.title_movie));
                    fragment = new MovieFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.nav_tv_show:
                    toolbar.setIcon(R.drawable.ic_outline_tv);
                    toolbar.setTitle(" " + getString(R.string.title_tv_show));
                    fragment = new TvShowFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_layout, fragment);
        transaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pref = new Pref(this);
        toolbar = getSupportActionBar();
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if (savedInstanceState == null){
            navView.setSelectedItemId(R.id.nav_movie);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.trans) {
            changelanguage();
        }
        return super.onOptionsItemSelected(item);
    }

    private void changelanguage() {
        int checked = pref.getLangPreference();
        String[] lang = new String[]{getResources().getString(R.string.en), getResources().getString(R.string.in)};
        final int[] choose = new int[1];

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(getString(R.string.lang));
        alert.setSingleChoiceItems(lang, checked, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                choose[0] = which;
            }
        });
        alert.setPositiveButton(getResources().getString(R.string.change), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setLocale(choose[0]);
                pref.setLangPreference(choose[0]);
                dialog.dismiss();
                refresh();
            }
        });
        alert.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = alert.create();
        alertDialog.show();
    }

    private void setLocale(int i) {
        String[] lang = new String[]{"en", "in"};
        String language = lang[i];
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = locale;
        res.updateConfiguration(conf, dm);
    }

    private void refresh() {
        finish();
        startActivity(getIntent());
    }

    public boolean doubleBackToExitPressedOnce = false;
    @Override
    protected void onResume() {
        super.onResume();
        this.doubleBackToExitPressedOnce = false;
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(a);
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(MainActivity.this, R.string.press_exit, Toast.LENGTH_SHORT).show();
    }
}

package com.example.movies;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;

import com.example.movies.preference.Pref;
import com.example.movies.reminder.DailyReminder;
import com.example.movies.reminder.MovieReleaseReminder;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {

    private Pref pref;
    private Switch swDaily, swRelease;
    private DailyReminder dailyReminder = new DailyReminder();
    private MovieReleaseReminder releaseReminder = new MovieReleaseReminder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        pref = new Pref(this);
        boolean isDaily = pref.getDailyPref();
        boolean isRelease = pref.getmovieDailyPref();

        ConstraintLayout changeLang = findViewById(R.id.ch_lang);
        swDaily = findViewById(R.id.sw_daily);
        swRelease = findViewById(R.id.sw_release);
        swDaily.setChecked(isDaily);
        swRelease.setChecked(isRelease);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.settings));
        }

        changeLang.setOnClickListener(changeListener);
        swDaily.setOnClickListener(dailyListener);
        swRelease.setOnClickListener(releaseListener);
    }

    private View.OnClickListener changeListener = view -> changeLanguage();

    private View.OnClickListener dailyListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            pref.setDailyPref(swDaily.isChecked());
            boolean b = pref.getDailyPref();
            if (b) {
                dailyReminder.activateDailyReminder(getApplicationContext());
            } else {
                dailyReminder.deactivateDailyReminder(getApplicationContext());
            }
        }
    };

    private View.OnClickListener releaseListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            pref.setmovieDailyPref(swRelease.isChecked());
            boolean b = pref.getmovieDailyPref();
            if (b) {
                releaseReminder.activateReleaseReminder(getApplicationContext());
            } else {
                releaseReminder.deactivateReleaseReminder(getApplicationContext());
            }
        }
    };

    private void changeLanguage() {
        int checked = pref.getLangPreference();
        String[] lang = new String[]{getResources().getString(R.string.en), getResources().getString(R.string.in)};
        final int[] choose = new int[1];

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(getString(R.string.lang));
        alert.setSingleChoiceItems(lang, checked, (dialogInterface, which) -> choose[0] = which);
        alert.setPositiveButton(getResources().getString(R.string.change), (dialog, which) -> {
            setLocale(choose[0]);
            pref.setLangPreference(choose[0]);
            dialog.dismiss();
            refresh();
        });
        alert.setNegativeButton(getResources().getString(R.string.cancel), (dialog, which) -> dialog.dismiss());
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
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

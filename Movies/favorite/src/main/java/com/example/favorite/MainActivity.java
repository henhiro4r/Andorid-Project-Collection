package com.example.favorite;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.favorite.adapter.ViewPagerAdapter;
import com.example.favorite.fragment.MovieFragment;
import com.example.favorite.fragment.TvShowFragment;
import com.example.favorite.preference.Pref;

import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private Pref pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pref = new Pref(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.favorite));
        }
        TabLayout favTabLayout = findViewById(R.id.tab_fav);
        ViewPager favViewPager = findViewById(R.id.fav_viewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MovieFragment(), getString(R.string.title_movie));
        adapter.addFragment(new TvShowFragment(), getString(R.string.title_tv_show));
        favViewPager.setAdapter(adapter);
        favTabLayout.setupWithViewPager(favViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.trans){
            changeLanguage();
        }
        return super.onOptionsItemSelected(item);
    }

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

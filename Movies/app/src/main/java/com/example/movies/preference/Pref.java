package com.example.movies.preference;

import android.content.Context;
import android.content.SharedPreferences;

public class Pref {
    private static final String PREFS = "app_pref";

    private String langPref = "langPref";
    private String dailyPref = "dailyPref";
    private String movieDailyPref = "movieDailyPref";

    private Context context;
    private SharedPreferences sharedPreferences;

    public Pref(Context context) {
        this.context = context;
    }

    public void setLangPreference(int value) {
        sharedPreferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(langPref, value);
        editor.apply();
    }

    public int getLangPreference(){
        sharedPreferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(langPref,0);
    }

    public void setDailyPref(Boolean value) {
        sharedPreferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(dailyPref, value);
        editor.apply();
    }

    public boolean getDailyPref() {
        sharedPreferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(dailyPref, false);
    }

    public void setmovieDailyPref(Boolean value) {
        sharedPreferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(movieDailyPref, value);
        editor.apply();
    }

    public boolean getmovieDailyPref() {
        sharedPreferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(movieDailyPref, false);
    }
}

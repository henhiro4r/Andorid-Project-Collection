package com.example.movies.preference;

import android.content.Context;
import android.content.SharedPreferences;

public class Pref {
    private static final String PREFS = "app_pref";

    private String langPref = "langPref";

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
}

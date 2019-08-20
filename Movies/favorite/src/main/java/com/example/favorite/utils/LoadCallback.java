package com.example.favorite.utils;

import android.database.Cursor;

public interface LoadCallback {
    void preExecute();
    void postExecute(Cursor cursor);
}

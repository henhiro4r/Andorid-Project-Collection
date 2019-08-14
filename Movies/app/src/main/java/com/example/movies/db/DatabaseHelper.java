package com.example.movies.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.movies.db.DatabaseContract.TableColumns;

import static com.example.movies.db.DatabaseContract.TABLE_MOVIE;
import static com.example.movies.db.DatabaseContract.TABLE_TVSHOW;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dbmoviecatalog";

    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE_MOVIE = "CREATE TABLE " + TABLE_MOVIE + " ( "
            + TableColumns.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TableColumns.OBJECT_ID + " TEXT NOT NULL, "
            + TableColumns.TITLE + " TEXT NOT NULL, "
            + TableColumns.DESCRIPTION + " TEXT NOT NULL, "
            + TableColumns.POPULAR + " TEXT NOT NULL, "
            + TableColumns.POSTER + " TEXT NOT NULL, "
            + TableColumns.COVER + " TEXT NOT NULL, "
            + TableColumns.RELEASE_YEAR + " TEXT NOT NULL)";

    private static final String CREATE_TABLE_TVSHOW = "CREATE TABLE " + TABLE_TVSHOW + " ( "
            + TableColumns.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TableColumns.OBJECT_ID + " TEXT NOT NULL, "
            + TableColumns.TITLE + " TEXT NOT NULL, "
            + TableColumns.DESCRIPTION + " TEXT NOT NULL, "
            + TableColumns.POPULAR + " TEXT NOT NULL, "
            + TableColumns.POSTER + " TEXT NOT NULL, "
            + TableColumns.COVER + " TEXT NOT NULL, "
            + TableColumns.RELEASE_YEAR + " TEXT NOT NULL)";

    DatabaseHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MOVIE);
        db.execSQL(CREATE_TABLE_TVSHOW);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TVSHOW);
        onCreate(db);
    }
}

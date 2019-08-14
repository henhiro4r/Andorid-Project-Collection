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

    private static final String CREATE_TABLE_MOVIE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            TABLE_MOVIE,
            TableColumns._ID,
            TableColumns.OBJECT_ID,
            TableColumns.TITLE,
            TableColumns.DESCRIPTION,
            TableColumns.POPULAR,
            TableColumns.POSTER,
            TableColumns.COVER,
            TableColumns.RELEASE_YEAR
    );

    private static final String CREATE_TABLE_TVSHOW = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            TABLE_TVSHOW,
            TableColumns._ID,
            TableColumns.OBJECT_ID,
            TableColumns.TITLE,
            TableColumns.DESCRIPTION,
            TableColumns.POPULAR,
            TableColumns.POSTER,
            TableColumns.COVER,
            TableColumns.RELEASE_YEAR
    );

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

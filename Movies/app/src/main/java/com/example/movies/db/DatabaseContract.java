package com.example.movies.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract{
    public static final String AUTHORITY = "com.example.movies";
    private static final String SCHEME = "content";
    public static final String TABLE_MOVIE = "Movie";
    public static final String TABLE_TVSHOW = "TvShow";

    public static final Uri CONTENT_MOVIE_URI = new Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_MOVIE)
            .build();

    public static final Uri CONTENT_SHOW_URI = new Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_TVSHOW)
            .build();

    static final class TableColumns implements BaseColumns{
        static final String COLUMN_ID = "_id";
        static final String OBJECT_ID = "object_id";
        static final String TITLE = "title";
        static final String DESCRIPTION = "description";
        static final String POPULAR = "popular";
        static final String POSTER = "poster";
        static final String COVER = "cover";
        static final String RELEASE_YEAR = "release_year";
    }

    public static String getColumnString(Cursor cursor, String columnName){
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName){
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static long getColumnLong(Cursor cursor, String columnName){
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }
}

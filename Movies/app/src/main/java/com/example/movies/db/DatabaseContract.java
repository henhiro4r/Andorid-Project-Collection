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

    public static final class TableColumns implements BaseColumns{
        public static final String OBJECT_ID = "object_id";
        public static final String TITLE = "title";
        public static final String DESCRIPTION = "description";
        public static final String POPULAR = "popular";
        public static final String POSTER = "poster";
        public static final String COVER = "cover";
        public static final String RELEASE_YEAR = "release_year";
    }

    public static String getColumnString(Cursor cursor, String columnName){
        return cursor.getString(cursor.getColumnIndex(columnName));
    }
}

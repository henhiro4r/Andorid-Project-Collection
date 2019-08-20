package com.example.movies.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.example.movies.db.FavoriteMovieHelper;
import com.example.movies.db.FavoriteTvShowHelper;
import com.example.movies.fragment.favorite.FavMovieFragment;
import com.example.movies.fragment.favorite.FavTvShowFragment;

import static com.example.movies.db.DatabaseContract.AUTHORITY;
import static com.example.movies.db.DatabaseContract.CONTENT_MOVIE_URI;
import static com.example.movies.db.DatabaseContract.CONTENT_SHOW_URI;
import static com.example.movies.db.DatabaseContract.TABLE_MOVIE;
import static com.example.movies.db.DatabaseContract.TABLE_TVSHOW;

public class MainProvider extends ContentProvider {

    private static final int MOVIE = 1;
    private static final int MOVIE_ID = 2;
    private static final int TV_SHOW = 3;
    private static final int TV_SHOW_ID = 4;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private FavoriteMovieHelper movieHelper;
    private FavoriteTvShowHelper tvShowHelper;

    static {
        // content://com.example.movies/movie
        sUriMatcher.addURI(AUTHORITY, TABLE_MOVIE, MOVIE);
        // content://com.example.movies/movie/id
        sUriMatcher.addURI(AUTHORITY, TABLE_MOVIE + "/#", MOVIE_ID);
        // content://com.example.movies/tvshow/
        sUriMatcher.addURI(AUTHORITY, TABLE_TVSHOW, TV_SHOW);
        // content://com.example.movies/tvshow/id
        sUriMatcher.addURI(AUTHORITY, TABLE_TVSHOW + "/#", TV_SHOW_ID);
    }

    @Override
    public boolean onCreate() {
        movieHelper = FavoriteMovieHelper.getInstance(getContext());
        tvShowHelper = FavoriteTvShowHelper.getInstance(getContext());
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] strings, String s, String[] strings1, String s1) {
        movieHelper.open();
        tvShowHelper.open();
        Cursor cursor;
        switch (sUriMatcher.match(uri)){
            case MOVIE:
                cursor = movieHelper.queryProvider();
                break;
            case MOVIE_ID:
                cursor = movieHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            case TV_SHOW:
                cursor = tvShowHelper.queryProvider();
                break;
            case TV_SHOW_ID:
                cursor = tvShowHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }
        return cursor;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        Uri uri1;
        movieHelper.open();
        tvShowHelper.open();
        long added;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                added = movieHelper.insertProvider(contentValues);
                uri1 = Uri.parse(CONTENT_MOVIE_URI + "/" + added);
                if (getContext() != null){
                    getContext().getContentResolver().notifyChange(CONTENT_MOVIE_URI, new FavMovieFragment.DataObserver(new Handler(), getContext()));
                }
                break;
            case TV_SHOW:
                added = tvShowHelper.insertProvider(contentValues);
                uri1 = Uri.parse(CONTENT_SHOW_URI + "/" + added);
                if (getContext() != null){
                    getContext().getContentResolver().notifyChange(CONTENT_SHOW_URI, new FavTvShowFragment.DataObserver(new Handler(), getContext()));
                }
                break;
            default:
                throw new SQLException("Failed to insert data " + uri);
        }
        return uri1;
    }

    @Override
    public int delete(@NonNull Uri uri, String s,  String[] strings) {
        int deleted ;
        movieHelper.open();
        tvShowHelper.open();
        switch (sUriMatcher.match(uri)){
            case MOVIE_ID:
                deleted = movieHelper.deleteProvider(uri.getLastPathSegment());
                if (getContext() != null){
                    getContext().getContentResolver().notifyChange(CONTENT_MOVIE_URI, new FavMovieFragment.DataObserver(new Handler(), getContext()));
                }
                break;
            case TV_SHOW_ID:
                deleted = tvShowHelper.deleteProvider(uri.getLastPathSegment());
                if (getContext() != null){
                    getContext().getContentResolver().notifyChange(CONTENT_SHOW_URI, new FavTvShowFragment.DataObserver(new Handler(), getContext()));
                }
                break;
            default:
                deleted = 0;
                break;
        }
        return deleted ;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}

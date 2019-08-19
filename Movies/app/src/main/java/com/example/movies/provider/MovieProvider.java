//package com.example.movies.provider;
//
//import android.content.ContentProvider;
//import android.content.ContentValues;
//import android.content.UriMatcher;
//import android.database.Cursor;
//import android.net.Uri;
//import android.support.annotation.NonNull;
//
//import com.example.movies.MainActivity;
//import com.example.movies.db.FavoriteMovieHelper;
//
//import static com.example.movies.db.DatabaseContract.AUTHORITY;
//import static com.example.movies.db.DatabaseContract.CONTENT_MOVIE_URI;
//import static com.example.movies.db.DatabaseContract.TABLE_MOVIE;
//
//public class MovieProvider extends ContentProvider {
//
//    private static final int MOVIE = 1;
//    private static final int MOVIE_ID = 2;
//    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
//    private FavoriteMovieHelper movieHelper;
//
//    @Override
//    public boolean onCreate() {
//        movieHelper = FavoriteMovieHelper.getInstance(getContext());
//        return true;
//    }
//
//    @Override
//    public Cursor query(@NonNull Uri uri, String[] strings, String s, String[] strings1, String s1) {
//        movieHelper.open();
//        Cursor cursor;
//        switch (sUriMatcher.match(uri)){
//            case MOVIE:
//                cursor = movieHelper;
//                break;
//            case MOVIE_ID:
//                cursor = movieHelper;
//                break;
//            default:
//                cursor = null;
//                break;
//        }
//
//        return cursor;
//    }
//
//    @Override
//    public String getType(@NonNull Uri uri) {
//        return null;
//    }
//
//    @Override
//    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
//        movieHelper.open();
//        long added;
//        switch (sUriMatcher.match(uri)) {
//            case MOVIE:
//                added = movieHelper;
//                break;
//            default:
//                added = 0;
//                break;
//        }
//        getContext().getContentResolver().notifyChange(CONTENT_MOVIE_URI, new MainActivity.Data);
//        return null;
//    }
//
//    @Override
//    public int delete(@NonNull Uri uri, String s,  String[] strings) {
//        return 0;
//    }
//
//    @Override
//    public int update(@NonNull Uri uri, ContentValues contentValues, String s, String[] strings) {
//        return 0;
//    }
//}

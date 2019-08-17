package com.example.movies.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.movies.model.Movie;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.movies.db.DatabaseContract.TABLE_MOVIE;
import static com.example.movies.db.DatabaseContract.TableColumns.OBJECT_ID;
import static com.example.movies.db.DatabaseContract.TableColumns.TITLE;
import static com.example.movies.db.DatabaseContract.TableColumns.DESCRIPTION;
import static com.example.movies.db.DatabaseContract.TableColumns.POPULAR;
import static com.example.movies.db.DatabaseContract.TableColumns.POSTER;
import static com.example.movies.db.DatabaseContract.TableColumns.COVER;
import static com.example.movies.db.DatabaseContract.TableColumns.RELEASE_YEAR;

public class FavoriteMovieHelper {
    private static final String DATABASE_TABLE = TABLE_MOVIE;
    private static DatabaseHelper databaseHelper;
    private static FavoriteMovieHelper INSTANCE;
    private static SQLiteDatabase database;

    public FavoriteMovieHelper(Context context){
        databaseHelper = new DatabaseHelper(context);
    }

    public static FavoriteMovieHelper getInstance(Context context){
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FavoriteMovieHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        databaseHelper.close();
        if (database.isOpen()){
            database.close();
        }
    }

    public ArrayList<Movie> getFavMovie(){
        ArrayList<Movie> movies = new ArrayList<>();
        Movie movie;
        Cursor cursor = database.query(DATABASE_TABLE, null, null, null, null, null, _ID + " DESC", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            do {
                movie = new Movie();
                movie.setId_movie(cursor.getString(cursor.getColumnIndexOrThrow(OBJECT_ID)));
                movie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                movie.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                movie.setPopularity(cursor.getString(cursor.getColumnIndexOrThrow(POPULAR)));
                movie.setPoster(cursor.getString(cursor.getColumnIndexOrThrow(POSTER)));
                movie.setCover(cursor.getString(cursor.getColumnIndexOrThrow(COVER)));
                movie.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_YEAR)));
                movie.setIsFav(1);
                movies.add(movie);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return movies;
    }

    public long addFavMovie(Movie movie){
        int fav = checker(movie.getId_movie());
        if (fav == 0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(OBJECT_ID, movie.getId_movie());
            contentValues.put(TITLE, movie.getTitle());
            contentValues.put(DESCRIPTION, movie.getDescription());
            contentValues.put(POPULAR, movie.getPopularity());
            contentValues.put(POSTER, movie.getPoster());
            contentValues.put(COVER, movie.getCover());
            contentValues.put(RELEASE_YEAR, movie.getReleaseDate());
            return database.insert(DATABASE_TABLE, null, contentValues);
        } else {
            return (long) 101;
        }
    }

    public int deleteFavMovie(String id) {
        return database.delete(DATABASE_TABLE, OBJECT_ID + " = '" + id + "'", null);
    }

    public int checker(String id){
        int response;
        String query = "SELECT * FROM " + DATABASE_TABLE + " WHERE " + OBJECT_ID + " = '" + id + "'";
        Cursor c = database.rawQuery(query, null);
        if (c.getCount() == 1){
            response = 1;
            c.close();
        } else {
            response = 0;
            c.close();
        }
        return response;
    }
}
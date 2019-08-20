package com.example.movies.helper;

import android.database.Cursor;

import com.example.movies.model.Movie;
import com.example.movies.model.TvShow;

import java.util.ArrayList;

import static com.example.movies.db.DatabaseContract.TableColumns.OBJECT_ID;
import static com.example.movies.db.DatabaseContract.TableColumns.TITLE;
import static com.example.movies.db.DatabaseContract.TableColumns.DESCRIPTION;
import static com.example.movies.db.DatabaseContract.TableColumns.POPULAR;
import static com.example.movies.db.DatabaseContract.TableColumns.POSTER;
import static com.example.movies.db.DatabaseContract.TableColumns.COVER;
import static com.example.movies.db.DatabaseContract.TableColumns.RELEASE_YEAR;

public class MappingHelper {
    public static ArrayList<Movie> mapCursorToArrayListMovie(Cursor cursor){
        ArrayList<Movie> movies = new ArrayList<>();
        while (cursor.moveToNext()){
            String id_movie = cursor.getString(cursor.getColumnIndexOrThrow(OBJECT_ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(TITLE));
            String popularity = cursor.getString(cursor.getColumnIndexOrThrow(POPULAR));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION));
            String poster = cursor.getString(cursor.getColumnIndexOrThrow(POSTER));
            String cover = cursor.getString(cursor.getColumnIndexOrThrow(COVER));
            String releaseDate = cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_YEAR));
            movies.add(new Movie(id_movie, title, popularity, description, poster, cover, releaseDate));
        }
        return movies;
    }

    public static ArrayList<TvShow> mapCursorToArrayListShow(Cursor cursor){
        ArrayList<TvShow> tvShows = new ArrayList<>();
        while (cursor.moveToNext()){
            String id_movie = cursor.getString(cursor.getColumnIndexOrThrow(OBJECT_ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(TITLE));
            String popularity = cursor.getString(cursor.getColumnIndexOrThrow(POPULAR));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION));
            String poster = cursor.getString(cursor.getColumnIndexOrThrow(POSTER));
            String cover = cursor.getString(cursor.getColumnIndexOrThrow(COVER));
            String releaseDate = cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_YEAR));
            tvShows.add(new TvShow(id_movie, title, popularity, description, poster, cover, releaseDate));
        }
        return tvShows;
    }
}

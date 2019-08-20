package com.example.favorite.utils;

import android.content.ContentValues;

import com.example.favorite.model.Movie;
import com.example.favorite.model.TvShow;

import static com.example.favorite.db.DatabaseContract.TableColumns.COVER;
import static com.example.favorite.db.DatabaseContract.TableColumns.DESCRIPTION;
import static com.example.favorite.db.DatabaseContract.TableColumns.OBJECT_ID;
import static com.example.favorite.db.DatabaseContract.TableColumns.POPULAR;
import static com.example.favorite.db.DatabaseContract.TableColumns.POSTER;
import static com.example.favorite.db.DatabaseContract.TableColumns.RELEASE_YEAR;
import static com.example.favorite.db.DatabaseContract.TableColumns.TITLE;

public class ContentValueHelper {
    public static ContentValues getContentValueMovie(Movie movie){
        ContentValues values = new ContentValues();
        values.put(OBJECT_ID, movie.getId_movie());
        values.put(TITLE, movie.getTitle());
        values.put(DESCRIPTION, movie.getDescription());
        values.put(POPULAR, movie.getPopularity());
        values.put(POSTER, movie.getPoster());
        values.put(COVER, movie.getCover());
        values.put(RELEASE_YEAR, movie.getReleaseDate());
        return values;
    }

    public static ContentValues getContentValueShow(TvShow tvShow){
        ContentValues values = new ContentValues();
        values.put(OBJECT_ID, tvShow.getId_show());
        values.put(TITLE, tvShow.getTitle());
        values.put(DESCRIPTION, tvShow.getDescription());
        values.put(POPULAR, tvShow.getPopularity());
        values.put(POSTER, tvShow.getPoster());
        values.put(COVER, tvShow.getCover());
        values.put(RELEASE_YEAR, tvShow.getReleaseDate());
        return values;
    }
}

package com.example.movies.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.movies.model.TvShow;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.movies.db.DatabaseContract.TABLE_TVSHOW;
import static com.example.movies.db.DatabaseContract.TableColumns.OBJECT_ID;
import static com.example.movies.db.DatabaseContract.TableColumns.TITLE;
import static com.example.movies.db.DatabaseContract.TableColumns.DESCRIPTION;
import static com.example.movies.db.DatabaseContract.TableColumns.POPULAR;
import static com.example.movies.db.DatabaseContract.TableColumns.POSTER;
import static com.example.movies.db.DatabaseContract.TableColumns.COVER;
import static com.example.movies.db.DatabaseContract.TableColumns.RELEASE_YEAR;

public class FavoriteTvShowHelper {
    private static final String DATABASE_TABLE = TABLE_TVSHOW;
    private static DatabaseHelper databaseHelper;
    private static FavoriteTvShowHelper INSTANCE;
    private static SQLiteDatabase database;

    private FavoriteTvShowHelper (Context context) { databaseHelper = new DatabaseHelper(context); }

    public static FavoriteTvShowHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FavoriteTvShowHelper(context);
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

    public ArrayList<TvShow> getFavShow() {
        ArrayList<TvShow> tvShows = new ArrayList<>();
        TvShow tvShow;
        Cursor cursor = database.query(DATABASE_TABLE, null, null, null, null, null, _ID + " DESC", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            do {
                tvShow = new TvShow();
                tvShow.setId_show(cursor.getString(cursor.getColumnIndexOrThrow(OBJECT_ID)));
                tvShow.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                tvShow.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                tvShow.setPopularity(cursor.getString(cursor.getColumnIndexOrThrow(POPULAR)));
                tvShow.setPoster(cursor.getString(cursor.getColumnIndexOrThrow(POSTER)));
                tvShow.setPoster(cursor.getString(cursor.getColumnIndexOrThrow(COVER)));
                tvShow.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_YEAR)));

                tvShows.add(tvShow);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return tvShows;
    }

    public long addFavShow(TvShow tvShow) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(OBJECT_ID, tvShow.getId_show());
        contentValues.put(TITLE, tvShow.getTitle());
        contentValues.put(DESCRIPTION, tvShow.getDescription());
        contentValues.put(POPULAR, tvShow.getPopularity());
        contentValues.put(POSTER, tvShow.getPoster());
        contentValues.put(COVER, tvShow.getCover());
        contentValues.put(RELEASE_YEAR, tvShow.getReleaseDate());
        return database.insert(DATABASE_TABLE, null, contentValues);
    }

    public int deleteFavShow(String id) {
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

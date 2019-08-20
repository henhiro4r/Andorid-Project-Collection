package com.example.movies.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.movies.db.DatabaseContract.TABLE_TVSHOW;
import static com.example.movies.db.DatabaseContract.TableColumns.OBJECT_ID;

public class FavoriteTvShowHelper {
    private static final String DATABASE_TABLE = TABLE_TVSHOW;
    private static DatabaseHelper databaseHelper;
    private static FavoriteTvShowHelper INSTANCE;
    private static SQLiteDatabase database;

    private FavoriteTvShowHelper(Context context) { databaseHelper = new DatabaseHelper(context); }

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

    public Cursor queryByIdProvider(String id){
        return database.query(DATABASE_TABLE, null, OBJECT_ID + " = ?", new String[]{id}, null, null, null, null);
    }

    public Cursor queryProvider(){
        return database.query(DATABASE_TABLE, null, null, null, null, null, OBJECT_ID + " ASC", null);
    }

    public long insertProvider(ContentValues values){
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int deleteProvider(String id){
        return database.delete(DATABASE_TABLE, OBJECT_ID + " = ?", new String[]{id});
    }
}

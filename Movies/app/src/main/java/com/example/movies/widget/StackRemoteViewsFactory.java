package com.example.movies.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.movies.R;
import com.example.movies.db.DatabaseContract;
import com.example.movies.db.DatabaseHelper;
import com.example.movies.model.Movie;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private final ArrayList<Movie> movies = new ArrayList<>();
    private final Context context;
    private Cursor cursor;

    StackRemoteViewsFactory(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {
        loadData();
    }

    @Override
    public void onDataSetChanged() {
        loadData();
    }


    private void loadData() {
        movies.clear();
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase database = helper.getWritableDatabase();
        cursor = database.query(DatabaseContract.TABLE_MOVIE, null, null, null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Movie m = new Movie(cursor);
                    movies.add(m);
                } while (cursor.moveToNext());
            }
        }
    }

    @Override
    public void onDestroy() {
        if (cursor != null) {
            cursor.close();
        }
        movies.clear();
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_item);
        String coverUrl = movies.get(i).getCover();
        String title = movies.get(i).getTitle();
        CharSequence titles = movies.get(i).getTitle();
        try {
            Bitmap bitmap = Glide.with(context)
                    .asBitmap()
                    .load(coverUrl)
                    .apply(new RequestOptions().centerCrop())
                    .submit()
                    .get();
            remoteViews.setImageViewBitmap(R.id.witem_cover, bitmap);
            remoteViews.setTextViewText(R.id.witem_title, titles);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        Bundle bundle = new Bundle();
        bundle.putString(FavoriteMovieWidget.EXTRA_ITEM, title);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(bundle);
        remoteViews.setOnClickFillInIntent(R.id.witem_cover, fillInIntent);
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}

package com.example.favorite.fragment;


import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.favorite.DetailActivity;
import com.example.favorite.R;
import com.example.favorite.adapter.MovieAdapter;
import com.example.favorite.model.Movie;
import com.example.favorite.utils.ItemClickSupport;
import com.example.favorite.utils.LoadCallback;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.example.favorite.db.DatabaseContract.CONTENT_MOVIE_URI;
import static com.example.favorite.utils.MappingHelper.mapCursorToArrayListMovie;

public class MovieFragment extends Fragment implements LoadCallback {

    private RecyclerView rvMovie;
    private ProgressBar pbMovie;
    private ArrayList<Movie> movie = new ArrayList<>();
    private MovieAdapter movieAdapter;
    private ImageView imgNoColl;
    private TextView tv_noColl;
    private static final String EXTRA_STATE = "EXTRA_STATE";

    public MovieFragment() {

    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imgNoColl = view.findViewById(R.id.noMovie);
        tv_noColl = view.findViewById(R.id.tv_noCollMovie);
        rvMovie = view.findViewById(R.id.rv_movie);
        pbMovie = view.findViewById(R.id.pb_favMovie);
        rvMovie.setLayoutManager(new LinearLayoutManager(getActivity()));
        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        DataObserver observer = new DataObserver(handler, getContext());
        if (getActivity() != null){
            getActivity().getContentResolver().registerContentObserver(CONTENT_MOVIE_URI, true, observer);
        }
        movieAdapter = new MovieAdapter(getActivity());
        rvMovie.setAdapter(movieAdapter);
        if (savedInstanceState == null){
            new LoadMovieAsync(getContext(), this).execute();
        } else {
            ArrayList<Movie> m = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (m != null){
                movieAdapter.setMovies(m);
            }
        }
        clickSupport();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, movieAdapter.getListMovie());
    }

    @Override
    public void onResume() {
        super.onResume();
        new LoadMovieAsync(getContext(), this).execute();
    }

    public void clickSupport() {
        ItemClickSupport.addTo(rvMovie).setOnItemClickListener((recyclerView, position, v) -> {
            Uri uri = Uri.parse(CONTENT_MOVIE_URI + "/" + movie.get(position).getId_movie());
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.setData(uri);
            intent.putExtra(DetailActivity.EXTRA_MOVIE, movie.get(position));
            startActivity(intent);
        });
    }

    @Override
    public void preExecute() {
        if (getActivity() != null){
            getActivity().runOnUiThread(()
                    -> pbMovie.setVisibility(View.VISIBLE));
        }
    }

    @Override
    public void postExecute(Cursor cursor) {
        pbMovie.setVisibility(View.GONE);
        movie = mapCursorToArrayListMovie(cursor);
        if (movie.size() > 0) {
            movieAdapter.setMovies(movie);
            noCollections(false);
        } else {
            rvMovie.setVisibility(View.INVISIBLE);
            noCollections(true);
        }
    }

    private void noCollections(boolean b) {
        if (b) {
            imgNoColl.setVisibility(View.VISIBLE);
            tv_noColl.setVisibility(View.VISIBLE);
        } else {
            imgNoColl.setVisibility(View.GONE);
            tv_noColl.setVisibility(View.GONE);
        }
    }

    private static class LoadMovieAsync extends AsyncTask<Void, Void, Cursor> {
        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadCallback> weakCallback;

        private LoadMovieAsync(Context context, LoadCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            Context context = weakContext.get();
            return context.getContentResolver().query(CONTENT_MOVIE_URI, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            weakCallback.get().postExecute(cursor);
        }
    }

    public static class DataObserver extends ContentObserver {
        final Context context;

        DataObserver(android.os.Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
        }
    }
}

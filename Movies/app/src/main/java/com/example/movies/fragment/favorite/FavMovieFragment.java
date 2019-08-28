package com.example.movies.fragment.favorite;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.movies.DetailActivity;
import com.example.movies.R;
import com.example.movies.adapter.MovieAdapter;
import com.example.movies.clicksupport.ItemClickSupport;
import com.example.movies.helper.LoadCallback;
import com.example.movies.model.Movie;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.example.movies.db.DatabaseContract.CONTENT_MOVIE_URI;
import static com.example.movies.helper.MappingHelper.mapCursorToArrayListMovie;

public class FavMovieFragment extends Fragment implements LoadCallback {

    private RecyclerView rvFavMovie;
    private ProgressBar pbFavMovie;
    private ArrayList<Movie> movie = new ArrayList<>();
    private MovieAdapter movieAdapter;
    private ImageView imgNoColl;
    private TextView tv_noColl;
    private static final String EXTRA_STATE = "EXTRA_STATE";

    public FavMovieFragment() {

    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fav_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imgNoColl = view.findViewById(R.id.no_collMovie);
        tv_noColl = view.findViewById(R.id.tv_noCollMovie);
        rvFavMovie = view.findViewById(R.id.favRv_movie);
        pbFavMovie = view.findViewById(R.id.pb_favMovie);
        rvFavMovie.setLayoutManager(new LinearLayoutManager(getActivity()));
        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        DataObserver observer = new DataObserver(handler, getContext());
        if (getActivity() != null){
            getActivity().getContentResolver().registerContentObserver(CONTENT_MOVIE_URI, true, observer);
        }
        movieAdapter = new MovieAdapter(getActivity());
        rvFavMovie.setAdapter(movieAdapter);

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

    private void clickSupport() {
        ItemClickSupport.addTo(rvFavMovie).setOnItemClickListener((recyclerView, i, v) -> {
            Uri uri = Uri.parse(CONTENT_MOVIE_URI + "/" + movie.get(i).getId_movie());
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.setData(uri);
            intent.putExtra(DetailActivity.EXTRA_MOVIE, movie.get(i));
            startActivity(intent);
        });
    }

    @Override
    public void preExecute() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(()
                    -> pbFavMovie.setVisibility(View.VISIBLE));
        }
    }

    @Override
    public void postExecute(Cursor cursor) {
        pbFavMovie.setVisibility(View.GONE);
        movie = mapCursorToArrayListMovie(cursor);
        if (movie.size() > 0){
            noCollections(false);
            movieAdapter.setMovies(movie);
        } else {
            rvFavMovie.setVisibility(View.INVISIBLE);
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

    public static class DataObserver extends ContentObserver{
        final Context context;

        public DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
        }
    }
}

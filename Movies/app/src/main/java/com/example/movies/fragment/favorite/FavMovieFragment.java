package com.example.movies.fragment.favorite;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.movies.R;
import com.example.movies.adapter.MovieAdapter;
import com.example.movies.db.FavoriteMovieHelper;
import com.example.movies.model.Movie;

import java.util.ArrayList;

public class FavMovieFragment extends Fragment {

    private RecyclerView rvFavMovie;
    private ProgressBar pbFavMovie;
    private ArrayList<Movie> movie = new ArrayList<>();
    private MovieAdapter movieAdapter;
//    private FavoriteMovieHelper movieHelper;

    public FavMovieFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_fav_movie, container, false);
        rvFavMovie = v.findViewById(R.id.favRv_movie);
        pbFavMovie = v.findViewById(R.id.pb_favMovie);
//        movieHelper = FavoriteMovieHelper.getInstance(getActivity());
//        movieHelper.open();
        showLoading(true);
        movieAdapter = new MovieAdapter(getActivity());
        movieAdapter.notifyDataSetChanged();
        return v;
    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        movieHelper.close();
//    }

    private void showLoading(Boolean state) {
        if (state) {
            pbFavMovie.setVisibility(View.VISIBLE);
        } else {
            pbFavMovie.setVisibility(View.GONE);
        }
    }
}

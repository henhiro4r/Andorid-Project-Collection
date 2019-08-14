package com.example.movies.fragment.favorite;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.movies.R;

public class FavMovieFragment extends Fragment {

    private RecyclerView rvFavMovie;
    private ProgressBar pbFavMovie;

    public FavMovieFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_fav_movie, container, false);
        rvFavMovie = v.findViewById(R.id.favRv_movie);
        pbFavMovie = v.findViewById(R.id.pb_favMovie);
        showLoading(true);
        return v;
    }

    private void showLoading(Boolean state) {
        if (state) {
            pbFavMovie.setVisibility(View.VISIBLE);
        } else {
            pbFavMovie.setVisibility(View.GONE);
        }
    }
}

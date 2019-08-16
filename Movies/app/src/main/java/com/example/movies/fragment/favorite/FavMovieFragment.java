package com.example.movies.fragment.favorite;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.movies.DetailActivity;
import com.example.movies.R;
import com.example.movies.adapter.MovieAdapter;
import com.example.movies.clicksupport.ItemClickSupport;
import com.example.movies.model.Movie;
import com.example.movies.viewmodel.FavoriteViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class FavMovieFragment extends Fragment {

    private RecyclerView rvFavMovie;
    private ProgressBar pbFavMovie;
    private ArrayList<Movie> movie = new ArrayList<>();
    private MovieAdapter movieAdapter;

    public FavMovieFragment() {

    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_fav_movie, container, false);
        rvFavMovie = v.findViewById(R.id.favRv_movie);
        pbFavMovie = v.findViewById(R.id.pb_favMovie);
        showLoading(true);
        movieAdapter = new MovieAdapter(getActivity());
        movieAdapter.notifyDataSetChanged();

        FavoriteViewModel favoriteViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(FavoriteViewModel.class);
        favoriteViewModel.setFavMovies();
        favoriteViewModel.getFavMovies().observe(getActivity(), loadMovie);

        rvFavMovie.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvFavMovie.setAdapter(movieAdapter);
        clickSupport();
        return v;
    }

    private Observer<ArrayList<Movie>> loadMovie = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(@Nullable ArrayList<Movie> movies) {
            if (movies != null) {
                movie.addAll(movies);
                movieAdapter.setMovies(movies);
                showLoading(false);
            }
        }
    };

    private void clickSupport() {
        ItemClickSupport.addTo(rvFavMovie).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int i, View v) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_MOVIE, movie.get(i));
                startActivity(intent);
            }
        });
    }

    private void showLoading(Boolean state) {
        if (state) {
            pbFavMovie.setVisibility(View.VISIBLE);
        } else {
            pbFavMovie.setVisibility(View.GONE);
        }
    }
}

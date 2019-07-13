package com.example.movies;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movies.adapter.MovieAdapter;
import com.example.movies.model.Movie;
import com.example.movies.model.MovieData;

import java.util.ArrayList;


public class MovieFragment extends Fragment {

    RecyclerView rvMovies;
    ArrayList<Movie> movies = new ArrayList<>();

    public MovieFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_movie, container, false);
        rvMovies = v.findViewById(R.id.rv_movie);
        rvMovies.setHasFixedSize(true);
        movies.addAll(MovieData.getListData());
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadData();
    }

    private void loadData() {
        rvMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
        MovieAdapter movieAdapter = new MovieAdapter(getActivity());
        movieAdapter.setMovies(movies);
        rvMovies.setAdapter(movieAdapter);

        ItemClickSupport.addTo(rvMovies).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int i, View v) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_MOVIE, movies.get(i));
                startActivity(intent);
            }
        });
    }
}

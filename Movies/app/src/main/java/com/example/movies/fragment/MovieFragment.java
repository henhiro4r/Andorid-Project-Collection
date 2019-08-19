package com.example.movies.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
import com.example.movies.viewmodel.MainViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class MovieFragment extends Fragment {

    private RecyclerView rvMovies;
    private ArrayList<Movie> movie = new ArrayList<>();
    private ProgressBar progressBar;
    private MovieAdapter movieAdapter;

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
        progressBar = view.findViewById(R.id.progressBar);
        rvMovies = view.findViewById(R.id.rv_movie);
        showLoading(true);
        movieAdapter = new MovieAdapter(getActivity());
        movieAdapter.notifyDataSetChanged();

        MainViewModel movieViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(MainViewModel.class);
        movieViewModel.setListMovies();
        movieViewModel.getListMovies().observe(getActivity(), loadMovie);

        rvMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMovies.setAdapter(movieAdapter);
        clickSupport();
    }

    private Observer<ArrayList<Movie>> loadMovie = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(@Nullable ArrayList<Movie> movies) {
            if (movies != null){
                movie.addAll(movies);
                movieAdapter.setMovies(movies);
                showLoading(false);
            }
        }
    };

    private void clickSupport() {
        ItemClickSupport.addTo(rvMovies).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
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
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void showAlert(){
        AlertDialog.Builder alert = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        alert.setTitle(getString(R.string.failToConnect));
        alert.setMessage(getString(R.string.conn_failed));
        alert.setPositiveButton(getResources().getString(R.string.close), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = alert.create();
        alertDialog.show();
    }
}

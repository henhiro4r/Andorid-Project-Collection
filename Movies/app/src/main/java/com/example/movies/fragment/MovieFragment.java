package com.example.movies.fragment;

import android.app.SearchManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.example.movies.DetailActivity;
import com.example.movies.R;
import com.example.movies.SearchActivity;
import com.example.movies.SettingsActivity;
import com.example.movies.adapter.MovieAdapter;
import com.example.movies.clicksupport.ItemClickSupport;
import com.example.movies.model.Movie;
import com.example.movies.viewmodel.MainViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

import static com.example.movies.db.DatabaseContract.CONTENT_MOVIE_URI;

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
        setHasOptionsMenu(true);
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
        ItemClickSupport.addTo(rvMovies).setOnItemClickListener((recyclerView, i, v) -> {
            Uri uri = Uri.parse(CONTENT_MOVIE_URI + "/" + movie.get(i).getId_movie());
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.setData(uri);
            intent.putExtra(DetailActivity.EXTRA_MOVIE, movie.get(i));
            startActivity(intent);
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        search(menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.trans) {
           Intent intent = new Intent(getActivity(), SettingsActivity.class);
           startActivity(intent);
           return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void search(Menu menu) {
        SearchManager searchManager;
        if (getContext() != null) {
            searchManager = (SearchManager) getContext().getSystemService(Context.SEARCH_SERVICE);
            if (searchManager != null) {
                SearchView searchView = (SearchView) (menu.findItem(R.id.search)).getActionView();
                searchView.setSearchableInfo(searchManager.getSearchableInfo(Objects.requireNonNull(getActivity()).getComponentName()));
                searchView.setIconifiedByDefault(true);
                searchView.setFocusable(true);
                searchView.setIconified(false);
                searchView.requestFocusFromTouch();
                searchView.setQueryHint(getString(R.string.search_movie));

                SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        Intent intent = new Intent(getActivity(), SearchActivity.class);
                        intent.putExtra(SearchActivity.EXTRA_MOVIE, s);
                        startActivity(intent);
                        keyboardHide(searchView);
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        return false;
                    }
                };
                searchView.setOnQueryTextListener(queryTextListener);
            }
        }
    }

    private void keyboardHide(SearchView searchView){
        if (getContext() != null) {
            InputMethodManager manager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
        }
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}

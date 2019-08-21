package com.example.movies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.example.movies.adapter.MovieAdapter;
import com.example.movies.adapter.TvShowAdapter;
import com.example.movies.clicksupport.ItemClickSupport;
import com.example.movies.model.Movie;
import com.example.movies.model.TvShow;
import com.example.movies.viewmodel.MainViewModel;

import java.util.ArrayList;

import static com.example.movies.db.DatabaseContract.CONTENT_MOVIE_URI;
import static com.example.movies.db.DatabaseContract.CONTENT_SHOW_URI;

public class SearchActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private SearchView searchView;
    private MainViewModel mainViewModel;
    private RecyclerView rvSearch;
    private ActionBar toolbar;
    public static final String EXTRA_MOVIE = "extra_movie";
    public static final String EXTRA_SHOW = "extra_show";
    private static final String API_KEY = "68eff651539ae197e48884a6d31d2059";
    private Boolean isMovie = false;
    private MovieAdapter movieAdapter;
    private TvShowAdapter tvShowAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchView = findViewById(R.id.searchView);
        progressBar = findViewById(R.id.pb_search);
        rvSearch = findViewById(R.id.rv_search);
        toolbar = getSupportActionBar();
        rvSearch.setLayoutManager(new LinearLayoutManager(this));
        showLoading(true);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        String query;
        if (getIntent().getStringExtra(EXTRA_MOVIE) != null) {
            isMovie = true;
            query = getIntent().getStringExtra(EXTRA_MOVIE);
            setupMovie(query);
        } else {
            isMovie = false;
            query = getIntent().getStringExtra(EXTRA_SHOW);
            setupShow(query);
        }
        setupSearchView(query);
    }

    private void setupSearchView(String query) {
        searchView.setQuery(query, false);
        searchView.setIconified(false);
        searchView.clearFocus();
        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String input) {
                if (isMovie) {
                    String url = "https://api.themoviedb.org/3/search/movie?api_key=" + API_KEY + "&language=en-US&query=" + input;
                    search(url);
                } else {
                    String url = "https://api.themoviedb.org/3/search/tv?api_key=" + API_KEY + "&language=en-US&query=" + input;
                    search(url);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    private void setupMovie(String query) {
        String url = "https://api.themoviedb.org/3/search/movie?api_key=" + API_KEY + "&language=en-US&query=" + query;
        toolbar.setTitle(getString(R.string.result_movie));
        search(url);
        movieAdapter = new MovieAdapter(this);
        rvSearch.setAdapter(movieAdapter);
    }

    private void setupShow(String query) {
        String url = "https://api.themoviedb.org/3/search/tv?api_key=" + API_KEY + "&language=en-US&query=" + query;
        toolbar.setTitle(getString(R.string.result_tv));
        search(url);
        tvShowAdapter = new TvShowAdapter(this);
        rvSearch.setAdapter(tvShowAdapter);
    }

    private void search(String url) {
        if (isMovie) {
            showLoading(true);
            mainViewModel.searchMovie(url);
            mainViewModel.getSearchMovieResult().observe(this, getMovieResult);
        } else {
            showLoading(true);
            mainViewModel.searchTvShow(url);
            mainViewModel.getSearchShowResult().observe(this, getShowResult);
        }
    }

    private Observer<ArrayList<Movie>> getMovieResult = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(@Nullable ArrayList<Movie> movies) {
            if (movies != null) {
                movieAdapter.setMovies(movies);
                movieAdapter.notifyDataSetChanged();
                showLoading(false);
                ItemClickSupport.addTo(rvSearch).setOnItemClickListener((recyclerView, position, v) -> {
                    Uri uri = Uri.parse(CONTENT_MOVIE_URI + "/" + movies.get(position).getId_movie());
                    Intent intent = new Intent(SearchActivity.this, DetailActivity.class);
                    intent.setData(uri);
                    intent.putExtra(DetailActivity.EXTRA_MOVIE, movies.get(position));
                    startActivity(intent);
                });
            }
        }
    };

    private Observer<ArrayList<TvShow>> getShowResult = new Observer<ArrayList<TvShow>>() {
        @Override
        public void onChanged(@Nullable ArrayList<TvShow> tvShows) {
            if (tvShows != null) {
                tvShowAdapter.setTvShows(tvShows);
                tvShowAdapter.notifyDataSetChanged();
                showLoading(false);
                ItemClickSupport.addTo(rvSearch).setOnItemClickListener((recyclerView, position, v) -> {
                    Uri uri = Uri.parse(CONTENT_SHOW_URI + "/" + tvShows.get(position).getId_show());
                    Intent intent = new Intent(SearchActivity.this, DetailActivity.class);
                    intent.setData(uri);
                    intent.putExtra(DetailActivity.EXTRA_SHOW, tvShows.get(position));
                    startActivity(intent);
                });
            }
        }
    };

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.trans) {
            Intent intent = new Intent(SearchActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

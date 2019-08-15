package com.example.movies.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.movies.db.FavoriteMovieHelper;
import com.example.movies.model.Movie;
import com.example.movies.model.TvShow;

import java.util.ArrayList;

public class FavoriteViewModel extends ViewModel {
    private static final String API_KEY = "68eff651539ae197e48884a6d31d2059";
    private MutableLiveData<ArrayList<Movie>> favMovies = new MutableLiveData<>();
    private MutableLiveData<ArrayList<TvShow>> favShows = new MutableLiveData<>();
    private FavoriteMovieHelper movieHelper;

    public FavoriteViewModel (Application application) {
//        super(application);
        movieHelper = new FavoriteMovieHelper(application);
    }

    public void setFavMovies(){

    }

    public LiveData<ArrayList<Movie>> getFavMovies () {
        return favMovies;
    }

    public void setFavShows() {

    }

    public LiveData<ArrayList<TvShow>> getFavShows () {
        return favShows;
    }
}

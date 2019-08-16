package com.example.movies.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.movies.db.FavoriteMovieHelper;
import com.example.movies.db.FavoriteTvShowHelper;
import com.example.movies.model.Movie;
import com.example.movies.model.TvShow;

import java.util.ArrayList;

public class FavoriteViewModel extends AndroidViewModel {
    private MutableLiveData<ArrayList<Movie>> favMovies = new MutableLiveData<>();
    private MutableLiveData<ArrayList<TvShow>> favShows = new MutableLiveData<>();
    private FavoriteMovieHelper movieHelper;
    private FavoriteTvShowHelper tvShowHelper;

    public FavoriteViewModel (Application application) {
        super(application);
        movieHelper = new FavoriteMovieHelper(application);
        tvShowHelper = new FavoriteTvShowHelper(application);
    }

    public void setFavMovies(){
        ArrayList<Movie> list = movieHelper.getFavMovie();
        favMovies.postValue(list);
    }

    public LiveData<ArrayList<Movie>> getFavMovies () {
        return favMovies;
    }

    public void setFavShows() {
        ArrayList<TvShow> list = tvShowHelper.getFavShow();
        favShows.postValue(list);
    }

    public LiveData<ArrayList<TvShow>> getFavShows () {
        return favShows;
    }
}
